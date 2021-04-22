package ru.w.automation.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.w.automation.domain.ProcessVariables;
import ru.w.automation.service.JiraIntegrationService;

@Component("closeIssueDelegate")
public class CloseIssueDelegate implements JavaDelegate {

    @Autowired
    private JiraIntegrationService jiraIntegrationService;

    @Override
    public void execute(DelegateExecution execution) {
        jiraIntegrationService.setIssueClosed(new ProcessVariables(execution).getIssueKey());
    }
}
