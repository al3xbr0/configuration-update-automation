package ru.w.automation.controller;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.w.automation.service.JiraIntegrationService;

@RestController
@SuppressWarnings("unused")
public class IssueRestController {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private JiraIntegrationService jiraIntegrationService;

    @PostMapping("api/jira/create/{issueKey:[a-zA-Z]+-\\d+}")
    public void create(@PathVariable("issueKey") String issueKey) {
        if (jiraIntegrationService.addActive(issueKey)) {
            runtimeService.createMessageCorrelation("Message_NewIssueCreated")
                    .setVariable("issueKey", issueKey)
                    .correlate();
        }
    }

    @PostMapping("api/jira/update/{issueKey:[a-zA-Z]+-\\d+}")
    public void update(@PathVariable("issueKey") String issueKey) {
        if (jiraIntegrationService.removeWaiting(issueKey)) {
            runtimeService.createMessageCorrelation("Message_IssueUpdated_" + issueKey)
                    .setVariable("issueClosed", false)
                    .correlate();
        }
    }

    @PostMapping("api/jira/cancel/{issueKey:[a-zA-Z]+-\\d+}")
    public void cancel(@PathVariable("issueKey") String issueKey) {
        if (jiraIntegrationService.removeWaiting(issueKey)) {
            runtimeService.createMessageCorrelation("Message_IssueUpdated_" + issueKey)
                    .setVariable("issueClosed", true)
                    .correlate();
        }
    }
}
