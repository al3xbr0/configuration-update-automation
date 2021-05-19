package automation.delegate.docs;

import automation.domain.ConfluencePage;
import automation.domain.ProcessVariables;
import automation.service.ConfluenceIntegrationService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("generateConfluenceTableDelegate")
public class GenerateConfluenceTableDelegate implements JavaDelegate {

    @Autowired
    private ConfluenceIntegrationService confluenceIntegrationService;

    @Override
    public void execute(DelegateExecution execution) {
        ProcessVariables variables = new ProcessVariables(execution);
        String confluenceTable = confluenceIntegrationService.generateConfluenceTable(variables.getRequest().getColumns());
        String issueKey = variables.getIssueKey();
        String spaceKey = issueKey.substring(0, issueKey.indexOf('-'));
        ConfluencePage page = new ConfluencePage(spaceKey, variables.getJsonConfigName(), confluenceTable);
        variables.setConfluencePage(page);
    }
}
