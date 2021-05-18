package automation.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import automation.domain.ProcessVariables;
import automation.service.ProcessInstanceService;

@Component("subscribeForUpdatesDelegate")
public class SubscribeForUpdatesDelegate implements JavaDelegate {

    @Autowired
    private ProcessInstanceService processInstanceService;

    @Override
    public void execute(DelegateExecution execution) {
        processInstanceService.addWaiting(new ProcessVariables(execution).getIssueKey());
    }
}
