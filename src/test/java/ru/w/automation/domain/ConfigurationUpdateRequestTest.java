package ru.w.automation.domain;

import com.atlassian.jira.rest.client.api.domain.Issue;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.w.automation.service.JiraRestService;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class ConfigurationUpdateRequestTest {

    @Autowired
    private JiraRestService jiraRests;

    private Issue is;

    @BeforeEach
    void setUp() {
        is = jiraRests.getIssue("GW-17");

    }

    @Test
    void of() {
        ConfigurationUpdateRequest request = ConfigurationUpdateRequest.of(is);

        assertEquals("sch", request.getSchemeName());
        assertEquals("tab", request.getTableName());

        Map<String, String> expectedColumns = new HashMap<>();
        expectedColumns.put("a", "int");
        expectedColumns.put("b", "varchar");
        expectedColumns.put("c", "uuid");
        assertEquals(expectedColumns, request.getColumns());

        assertEquals(55, request.getFrequency());
        assertTrue(request.isCreateSnapshots());
    }
}