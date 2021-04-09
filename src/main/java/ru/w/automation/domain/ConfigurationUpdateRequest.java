package ru.w.automation.domain;


import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueField;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static ru.w.automation.domain.ConfigurationUpdateFieldType.*;

public class ConfigurationUpdateRequest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationUpdateRequest.class);

    private static final List<ConfigurationUpdateFieldType> REQUIRED_FIELDS = List.of(SCHEME, TABLE, FREQUENCY, CREATE_SNAPSHOTS);

    private final String issueKey;
    private final String schemeName;
    private final String tableName;
    //private final Map<String, String> columns;
    private final Set<Column> columns; //TODO
    private final int frequency;
    private final boolean createSnapshots;

    public String getSchemeName() {
        return schemeName;
    }

    public String getTableName() {
        return tableName;
    }

    public Set<Column> getColumns() {
        return columns;
    }

    public int getFrequency() {
        return frequency;
    }

    public boolean isCreateSnapshots() {
        return createSnapshots;
    }

    public boolean areColumnsSpecified() {
        return Objects.nonNull(columns);
    }

    public ConfigurationUpdateRequest(String issueKey, String schemeName, String tableName, Set<Column> columns, int frequency, boolean createSnapshots) {
        this.issueKey = issueKey;
        this.schemeName = schemeName;
        this.tableName = tableName;
        this.columns = columns;
        this.frequency = frequency;
        this.createSnapshots = createSnapshots;
    }

    public static ConfigurationUpdateRequest of(Issue issue) {
        LOGGER.info("Building request from issue {}", issue.getKey());

        Map<ConfigurationUpdateFieldType, Object> fields = StreamSupport.stream(issue.getFields().spliterator(), false)
                .filter(
                        field -> fromJiraName(field.getName()) != UNWANTED_FIELD
                )
                .collect(
                        Collectors.toMap(
                                field -> fromJiraName(field.getName()),
                                IssueField::getValue
                        )
                );
        if (!fields.keySet().containsAll(REQUIRED_FIELDS)) {
            LOGGER.error("Some required fields are missing in issue {}", issue.getKey());
            throw new IllegalArgumentException("Some of required fields are not provided");
        }

        String schemeName = (String) fields.get(SCHEME);
        String tableName = (String) fields.get(TABLE);
        int frequency = ((Double) fields.get(FREQUENCY))
                .intValue();

        boolean createSnapshots = false;
        try {
            createSnapshots =
                    "yes".equalsIgnoreCase(
                            ((JSONObject) fields.get(CREATE_SNAPSHOTS))
                                    .getString("value")
                    );
        } catch (JSONException e) {
            LOGGER.error("Something wrong with \"Extraction type\" field. Check your Jira Custom Fields settings", e);
        }

        Set<Column> columns = null;
        String columnsFieldStr = (String) fields.get(COLUMNS);
        if (Objects.nonNull(columnsFieldStr)) {
            String[] columnsItems = columnsFieldStr.split("[,;\\s]\\s*");
            columns = Arrays.stream(columnsItems)
                    .map(
                            item -> {
                                String[] kv = item.split(":");
                                return new Column(kv[0], kv[1]);
                            }
                    )
                    .collect(
                            Collectors.toSet()
                    );
        }

        return new ConfigurationUpdateRequest(issue.getKey(), schemeName, tableName, columns, frequency, createSnapshots);
    }
}
