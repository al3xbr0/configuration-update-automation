package automation.delegate.docs;

import automation.domain.ConfluencePage;
import automation.domain.ProcessVariables;
import automation.service.ConfluenceIntegrationService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("updatePageDelegate")
public class UpdatePageDelegate implements JavaDelegate {

    @Autowired
    private ConfluenceIntegrationService confluenceIntegrationService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        ConfluencePage page = new ProcessVariables(execution).getConfluencePage();
        confluenceIntegrationService.updatePage(page.getId(), page.getTitle(), page.getBody(), page.getVersion());
    }
}
