package ru.w.automation.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.w.automation.domain.ProcessVariables;
import ru.w.automation.service.GitLabIntegrationService;

@Component("getMergeRequestStateDelegate")
public class GetMergeRequestStateDelegate implements JavaDelegate {

    @Autowired
    private GitLabIntegrationService gitLabIntegrationService;

    @Override
    public void execute(DelegateExecution execution) throws GitLabApiException {
        ProcessVariables variables = new ProcessVariables(execution);
        int iid = variables.getMergeRequestIid();

        boolean merged = gitLabIntegrationService.checkIfMerged(iid);
        if (!merged) {
            variables.setMergeRequestOpen(gitLabIntegrationService.checkIfOpen(iid));
        }
        variables.setMergeRequestMerged(merged);
    }
}
