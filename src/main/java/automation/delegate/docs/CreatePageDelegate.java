package automation.delegate.docs;

import automation.domain.ConfluencePage;
import automation.domain.ProcessVariables;
import automation.service.ConfluenceIntegrationService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("createPageDelegate")
public class CreatePageDelegate implements JavaDelegate {

    @Autowired
    private ConfluenceIntegrationService confluenceIntegrationService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        ConfluencePage page = new ProcessVariables(execution).getConfluencePage();
        confluenceIntegrationService.createPage(page.getSpaceKey(), page.getTitle(), page.getBody());
    }
}
