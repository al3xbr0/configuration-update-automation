package automation.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import automation.domain.ProcessVariables;
import automation.service.JenkinsIntegrationService;

@Component("callReleaseTaskDelegate")
public class CallReleaseTaskDelegate implements JavaDelegate {

    @Autowired
    private JenkinsIntegrationService jenkinsIntegrationService;

    @Override
    public void execute(DelegateExecution execution) {
        ProcessVariables variables = new ProcessVariables(execution);
        jenkinsIntegrationService.releaseTask(variables.getJsonConfigName());
    }
}
