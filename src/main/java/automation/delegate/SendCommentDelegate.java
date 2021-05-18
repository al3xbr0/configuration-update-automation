package automation.delegate;

import automation.domain.ProcessVariables;
import automation.domain.ValidationStatus;
import automation.service.JiraIntegrationService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("sendCommentDelegate")
public class SendCommentDelegate implements JavaDelegate {

    @Autowired
    private JiraIntegrationService jiraIntegrationService;

    @Override
    public void execute(DelegateExecution execution) {
        ProcessVariables variables = new ProcessVariables(execution);
        String issueKey = variables.getIssueKey();
        ValidationStatus validationStatus = variables.getValidationStatus();
        jiraIntegrationService.addComment(
                issueKey,
                validationStatus.getValidationComment()
        );
        if (!validationStatus.isValidationSuccessful()) {
            jiraIntegrationService.setIssueToDo(issueKey);
        }
    }
}
