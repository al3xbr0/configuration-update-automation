package ru.w.automation.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.w.automation.service.JiraRestService;

@Component
public class SendCommentDelegate implements JavaDelegate {

    @Autowired
    private JiraRestService jiraRestService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        String issueKey = (String) execution.getVariable("issueKey");
        String comment = (String) execution.getVariable("comment");
        jiraRestService.addComment(issueKey, comment);
    }
}
