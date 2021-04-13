package ru.w.automation.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.w.automation.domain.ConfigurationUpdateRequest;
import ru.w.automation.domain.ProcessVariables;
import ru.w.automation.domain.ValidationStatus;
import ru.w.automation.service.JiraIntegrationService;

@Component("buildRequestDelegate")
public class BuildRequestDelegate implements JavaDelegate {

    @Autowired
    private JiraIntegrationService jiraIntegrationService;

    @Override
    public void execute(DelegateExecution execution) {
        ProcessVariables variables = new ProcessVariables(execution);
        String issueKey = variables.getIssueKey();
        ConfigurationUpdateRequest request = ConfigurationUpdateRequest.of(jiraIntegrationService.getIssue(issueKey));

        variables.setRequest(request);
        variables.setValidationStatus(new ValidationStatus());
    }
}
