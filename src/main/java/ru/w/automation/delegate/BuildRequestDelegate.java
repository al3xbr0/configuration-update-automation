package ru.w.automation.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.w.automation.domain.ConfigurationUpdateRequest;
import ru.w.automation.domain.ValidationStatus;
import ru.w.automation.service.JiraIntegrationService;
//import ru.w.automation.service.RequestService;

@Component
public class BuildRequestDelegate implements JavaDelegate {

    @Autowired
    private JiraIntegrationService jiraIntegrationService;

    @Override
    public void execute(DelegateExecution execution) {
        String issueKey = (String) execution.getVariable("issueKey");
        ConfigurationUpdateRequest request = ConfigurationUpdateRequest.of(
                jiraIntegrationService.getIssue(issueKey)
        );
        execution.setVariable("request", request);
        execution.setVariable("validationStatus", new ValidationStatus());
    }
}
