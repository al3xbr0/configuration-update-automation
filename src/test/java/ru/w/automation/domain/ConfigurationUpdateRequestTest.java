package ru.w.automation.domain;

import com.atlassian.jira.rest.client.api.domain.Issue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.w.automation.service.JiraIntegrationService;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class ConfigurationUpdateRequestTest {

    @Autowired
    private JiraIntegrationService jiraRests;

    private Issue issue;

    @BeforeEach
    void setUp() {
        issue = jiraRests.getIssue("GW-17");
    }

    @Test
    void of() {
        ConfigurationUpdateRequest request = ConfigurationUpdateRequest.of(issue);

        assertEquals("sch", request.getSchemaName());
        assertEquals("tab", request.getTableName());

        Collection<Column> expectedColumns = List.of(
                new Column("a", "int"),
                new Column("b", "varchar"),
                new Column("c", "uuid")
        );
        assertEquals(expectedColumns, request.getColumns());

        assertEquals(55, request.getFrequency());
        assertTrue(request.isCreateSnapshots());
    }
}