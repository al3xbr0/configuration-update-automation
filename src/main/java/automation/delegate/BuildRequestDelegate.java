package automation.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import automation.domain.ConfigurationUpdateRequest;
import automation.domain.ProcessVariables;
import automation.domain.ValidationStatus;
import automation.service.JiraIntegrationService;

@Component("buildRequestDelegate")
public class BuildRequestDelegate implements JavaDelegate {

    @Autowired
    private JiraIntegrationService jiraIntegrationService;

    @Override
    public void execute(DelegateExecution execution) {
        ProcessVariables variables = new ProcessVariables(execution);
        String issueKey = variables.getIssueKey();

        jiraIntegrationService.setIssueInProgress(issueKey);
        ConfigurationUpdateRequest request = ConfigurationUpdateRequest.of(jiraIntegrationService.getIssue(issueKey));

        variables.setRequest(request);
        variables.setValidationStatus(new ValidationStatus());
    }
}
