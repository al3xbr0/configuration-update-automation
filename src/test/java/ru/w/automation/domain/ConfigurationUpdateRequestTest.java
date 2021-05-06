package ru.w.automation.domain;

import com.atlassian.jira.rest.client.api.domain.Issue;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.w.automation.service.JiraIntegrationService;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class ConfigurationUpdateRequestTest {

    @Autowired
    private JiraIntegrationService jiraIntegrationService;

    private Issue issue;

    @BeforeEach
    void setUp() {
        issue = jiraIntegrationService.getIssue("TEST-1");
    }

    @Test
    void of() {
        ConfigurationUpdateRequest request = ConfigurationUpdateRequest.of(issue);

        assertEquals("public", request.getSchemaName());
        assertEquals("test_users", request.getTableName());

        Collection<Column> expectedColumns = List.of(
                new Column("id", "integer"),
                new Column("first_name", "character varying", 32),
                new Column("last_name", "varchar", 32),
                new Column("address", "varchar", 64)
        );
        assertEquals(expectedColumns, request.getColumns());

        assertEquals(42, request.getFrequency());
        assertFalse(request.isCreateSnapshots());
        try {
            System.out.println(request.serializeConfigToJSON());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}