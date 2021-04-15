package ru.w.automation.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.w.automation.domain.ProcessVariables;
import ru.w.automation.service.JenkinsIntegrationService;

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
