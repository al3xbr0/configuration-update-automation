package automation.delegate.docs;

import automation.domain.ConfluencePage;
import automation.domain.ProcessVariables;
import automation.service.ConfluenceIntegrationService;
import com.github.crob1140.confluence.content.Content;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("getPageDelegate")
public class GetPageDelegate implements JavaDelegate {

    @Autowired
    private ConfluenceIntegrationService confluenceIntegrationService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        ConfluencePage page = new ProcessVariables(execution).getConfluencePage();
        Content content = confluenceIntegrationService.getPage(page.getSpaceKey(), page.getTitle());
        if (content != null) {
            page.setId(content.getId());
            page.setVersion(content.getVersion().getNumber());
        }
    }
}
