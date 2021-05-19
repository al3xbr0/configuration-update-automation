package automation.service;

import automation.domain.Column;
import com.github.crob1140.confluence.ConfluenceClient;
import com.github.crob1140.confluence.content.Content;
import com.github.crob1140.confluence.content.ContentBodyType;
import com.github.crob1140.confluence.content.StandardContentType;
import com.github.crob1140.confluence.content.expand.ExpandedContentProperties;
import com.github.crob1140.confluence.errors.ConfluenceRequestException;
import com.github.crob1140.confluence.requests.CreateContentRequest;
import com.github.crob1140.confluence.requests.GetContentRequest;
import com.github.crob1140.confluence.requests.UpdateContentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ConfluenceIntegrationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfluenceIntegrationService.class);

    private static final StandardContentType DEFAULT_CONTENT_TYPE = StandardContentType.PAGE;
    private static final ContentBodyType DEFAULT_CONTENT_BODY_TYPE = ContentBodyType.STORAGE;

    private static final String TABLE_HEAD = "<table data-layout=\"default\"><colgroup><col style=\"width: 340.0px;\" /><col style=\"width: 340.0px;\" /></colgroup>\n" +
            "<tbody>\n" +
            "<tr>\n" +
            "<th><p style=\"text-align: center;\"><strong>Column name</strong></p></th>\n" +
            "<th><p style=\"text-align: center;\"><strong>Column data type</strong></p></th>\n" +
            "</tr>\n";
    private static final String TABLE_ROW_FORMATTER = "<tr>\n" +
            "<td><p>%s</p></td>\n" +
            "<td>\n<p>%s</p></td>\n" +
            "</tr>\n";
    private static final String TABLE_TAIL = "</tbody>\n" +
            "</table>";


    @Autowired
    private ConfluenceClient confluenceClient;

    public Content getPage(String spaceKey, String title) throws ConfluenceRequestException {
        LOGGER.info("Getting Confluence page '{}' in space {}", title, spaceKey);
        List<Content> page = confluenceClient.getContent(
                new GetContentRequest.Builder()
                        .setSpaceKey(spaceKey)
                        .setTitle(title)
                        .setExpandedProperties(new ExpandedContentProperties.Builder()
                                .addVersion()
                                .build())
                        .setLimit(1)
                        .build()
        );
        return page.isEmpty() ? null : page.get(0);
    }

    public void createPage(String spaceKey, String title, String body) throws ConfluenceRequestException {
        LOGGER.info("Creating Confluence page '{}' in space {}", title, spaceKey);
        confluenceClient.createContent(
                new CreateContentRequest.Builder()
                        .setType(DEFAULT_CONTENT_TYPE)
                        .setSpaceKey(spaceKey)
                        .setTitle(title)
                        .setBody(DEFAULT_CONTENT_BODY_TYPE, body)
                        .build()
        );
    }

    public void updatePage(String id, String title, String body, int currentVersion) throws ConfluenceRequestException {
        LOGGER.info("Updating Confluence page '{}'", title);
        confluenceClient.updateContent(
                new UpdateContentRequest.Builder()
                        .setId(id)
                        .setType(DEFAULT_CONTENT_TYPE)
                        .setTitle(title)
                        .setBody(DEFAULT_CONTENT_BODY_TYPE, body)
                        .setVersion(currentVersion + 1)
                        .build()
        );
    }

    public String generateConfluenceTable(Collection<Column> columns) {
        LOGGER.info("Generating Confluence table");
        StringBuilder table = new StringBuilder(TABLE_HEAD);
        for (Column column : columns) {
            String row = String.format(TABLE_ROW_FORMATTER, column.getName(), column.getPrintableDataType());
            table.append(row);
        }
        table.append(TABLE_TAIL);
        return table.toString();
    }
}
