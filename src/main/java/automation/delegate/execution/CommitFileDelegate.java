package automation.delegate.execution;

import automation.domain.ConfigurationUpdateRequest;
import automation.domain.ProcessVariables;
import automation.service.GitLabIntegrationService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("commitFileDelegate")
public class CommitFileDelegate implements JavaDelegate {

    @Autowired
    private GitLabIntegrationService gitLabIntegrationService;

    @Override
    public void execute(DelegateExecution execution) throws GitLabApiException {
        ProcessVariables variables = new ProcessVariables(execution);
        ConfigurationUpdateRequest request = variables.getRequest();
        String jsonConfig = variables.getJsonConfig();

        String jsonConfigName = request.getSchemaName() + "." + request.getTableName();
        String tempBranchName = GitLabIntegrationService.convertStringToBranchName(request.getSummary());
        gitLabIntegrationService.commitFile(
                tempBranchName,
                variables.getIssueKey(),
                jsonConfigName + ".json",
                jsonConfig
        );
        variables.setJsonConfigName(jsonConfigName);
        variables.setTempBranchName(tempBranchName);
    }
}
