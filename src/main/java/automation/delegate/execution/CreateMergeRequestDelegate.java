package automation.delegate.execution;

import automation.domain.ProcessVariables;
import automation.service.GitLabIntegrationService;
import automation.service.JiraIntegrationService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.MergeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("createMergeRequestDelegate")
public class CreateMergeRequestDelegate implements JavaDelegate {

    @Autowired
    private GitLabIntegrationService gitLabIntegrationService;
    @Autowired
    private JiraIntegrationService jiraIntegrationService;

    @Override
    public void execute(DelegateExecution execution) throws GitLabApiException {
        ProcessVariables variables = new ProcessVariables(execution);

        MergeRequest mergeRequest = gitLabIntegrationService.createMergeRequest(variables.getTempBranchName());
        variables.setMergeRequestIid(mergeRequest.getIid());
        variables.setMergeRequestWebUrl(mergeRequest.getWebUrl());

        jiraIntegrationService.setIssueInReview(variables.getIssueKey());
    }
}
