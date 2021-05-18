package automation.domain;


import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static automation.domain.ConfigurationUpdateFieldType.*;

public class ConfigurationUpdateRequest implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationUpdateRequest.class);

    private static final List<ConfigurationUpdateFieldType> REQUIRED_FIELDS = List.of(SCHEMA, TABLE, FREQUENCY, CREATE_SNAPSHOTS);

    private static final Pattern COLUMNS_FIELD_CORRECT_PATTERN =
            Pattern.compile(
                    "^\\s*(?:\\w+\\s*:(?:\\w|\\s)*\\w+\\s*(?:|\\(\\s*\\d+\\s*\\)\\s*)(?:,\\s*(?!$)|$))+$"
            );
    private static final Pattern COLUMNS_ITEMS_PATTERN =
            Pattern.compile(
                    "(?<name>\\w+)\\s*:\\s*(?<type>(?:\\w\\s*)*\\w+)\\s*(?:\\(\\s*(?<length>\\d+)\\s*\\))?"
            );


    private final String issueSummary;
    private final String issueReporter;

    private final String schemaName;
    private final String tableName;
    private final Collection<Column> columns;
    private final int frequency;
    private final boolean createSnapshots;

    @JsonIgnore
    public String getSummary() {
        return issueSummary;
    }

    @JsonIgnore
    public String getReporterName() {
        return issueReporter;
    }


    public String getSchemaName() {
        return schemaName;
    }

    public String getTableName() {
        return tableName;
    }

    public Collection<Column> getColumns() {
        return columns;
    }

    public int getFrequency() {
        return frequency;
    }

    public boolean isCreateSnapshots() {
        return createSnapshots;
    }

    @SuppressWarnings("unused")
    public boolean areColumnsSpecified() {
        return !columns.isEmpty();
    }

    public ConfigurationUpdateRequest(String issueSummary, String issueReporter,
                                      String schemaName, String tableName,
                                      Collection<Column> columns, int frequency, boolean createSnapshots) {
        this.issueSummary = issueSummary;
        this.issueReporter = issueReporter;
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.columns = columns;
        this.frequency = frequency;
        this.createSnapshots = createSnapshots;
    }

    public static ConfigurationUpdateRequest of(Issue issue) {
        LOGGER.info("Building request from issue {}", issue.getKey());

        Map<ConfigurationUpdateFieldType, Object> fields =
                StreamSupport.stream(issue.getFields().spliterator(), false)
                        .filter(
                                field -> fromJiraName(field.getName()) != UNWANTED_FIELD && field.getValue() != null
                        )
                        .collect(
                                Collectors.toMap(
                                        field -> fromJiraName(field.getName()),
                                        IssueField::getValue
                                )
                        );
        if (!fields.keySet().containsAll(REQUIRED_FIELDS)) {
            LOGGER.error("Some required fields are missing in the issue");
            throw new IllegalArgumentException("Some of required fields are not provided");
        }

        String schemaName = (String) fields.get(SCHEMA);
        String tableName = (String) fields.get(TABLE);
        int frequency = ((Double) fields.get(FREQUENCY)).intValue();

        boolean createSnapshots = false;
        try {
            createSnapshots =
                    "yes".equalsIgnoreCase(
                            ((JSONObject) fields.get(CREATE_SNAPSHOTS)).getString("value")
                    );
        } catch (JSONException e) {
            LOGGER.warn("Something wrong with \"Extraction type\" field. Check your Jira Custom Fields settings." +
                    " Default value (false) has been assigned", e);
        }

        Collection<Column> columns = new ArrayList<>();
        String columnsFieldStr = (String) fields.get(COLUMNS);
        try {
            if (columnsFieldStr != null) {
                if (!COLUMNS_FIELD_CORRECT_PATTERN.matcher(columnsFieldStr).matches()) {
                    throw new IllegalArgumentException("\"Set of columns\" field doesn't match ");
                }
                Matcher columnsItemsMatcher = COLUMNS_ITEMS_PATTERN.matcher(columnsFieldStr);
                while (columnsItemsMatcher.find()) {
                    String name = columnsItemsMatcher.group("name");
                    String type = columnsItemsMatcher.group("type").replaceAll("\\s{2,}", " ");
                    String length = columnsItemsMatcher.group("length");
                    Column currentColumn;
                    if (length != null) {
                        currentColumn = new Column(name, type, Integer.valueOf(length));
                    } else {
                        currentColumn = new Column(name, type);
                    }
                    columns.add(currentColumn);
                }
                LOGGER.info("\"Set of columns\" field parsed successfully");
            } else {
                LOGGER.info("No columns to parse");
            }
        } catch (IllegalArgumentException e) {
            LOGGER.warn("Couldn't parse \"Set of columns\" field due to incorrect format", e);
        }

        return new ConfigurationUpdateRequest(issue.getSummary(),
                Objects.requireNonNull(issue.getReporter())
                        .getDisplayName(),
                schemaName, tableName, columns, frequency, createSnapshots);
    }

    @SuppressWarnings("unused")
    public String serializeConfigToJSON() throws JsonProcessingException {
        LOGGER.info("Converting request to JSON");
        return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
    }
}
