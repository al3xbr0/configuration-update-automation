package ru.w.automation.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.w.automation.domain.ValidationStatus;
import ru.w.automation.service.JiraIntegrationService;

@Component("sendCommentDelegate")
public class SendCommentDelegate implements JavaDelegate {

    @Autowired
    private JiraIntegrationService jiraIntegrationService;

    @Override
    public void execute(DelegateExecution execution) {
        String issueKey = (String) execution.getVariable("issueKey");
        String comment = ((ValidationStatus) execution.getVariable("validationStatus")).getValidationComment();
        jiraIntegrationService.addComment(issueKey, comment);
    }
}
