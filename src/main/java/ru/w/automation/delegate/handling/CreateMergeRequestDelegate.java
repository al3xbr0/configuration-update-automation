package ru.w.automation.delegate.handling;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.w.automation.domain.ProcessVariables;
import ru.w.automation.service.GitLabIntegrationService;

@Component("createMergeRequestDelegate")
public class CreateMergeRequestDelegate implements JavaDelegate {

    @Autowired
    private GitLabIntegrationService gitLabIntegrationService;

    @Override
    public void execute(DelegateExecution execution) throws GitLabApiException {
        ProcessVariables variables = new ProcessVariables(execution);

        int mergeRequestIid = gitLabIntegrationService.createMergeRequest(variables.getTempBranchName());
        variables.setMergeRequestIid(mergeRequestIid);
    }
}
