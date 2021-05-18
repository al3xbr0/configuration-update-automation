package automation.delegate;

import automation.domain.ProcessVariables;
import automation.service.JenkinsIntegrationService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
