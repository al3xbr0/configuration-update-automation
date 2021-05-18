package automation.service;

import automation.domain.ProcessConstants;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ProcessInstanceService {

    @Autowired
    private RuntimeService runtimeService;

    private final Set<String> instancesRunning = new HashSet<>();
    private final Set<String> instancesWaiting = new HashSet<>();

    public void startProcessInstance(String issueKey) {
        if (instancesRunning.add(issueKey)) {
            runtimeService.createProcessInstanceByKey("ConfigurationUpdateMainProcess")
                    .setVariable(ProcessConstants.VAR_NAME_ISSUE_KEY, issueKey)
                    .execute();
        }
    }

    public void updateProcessInstance(String issueKey) {
        if (instancesWaiting.remove(issueKey)) {
            runtimeService.createMessageCorrelation(ProcessConstants.MSG_NAME_ISSUE_UPDATED_ + issueKey)
                    .setVariable(ProcessConstants.VAR_NAME_ISSUE_CLOSED, false)
                    .correlate();
        }
    }

    public void cancelProcessInstance(String issueKey) {
        if (instancesWaiting.remove(issueKey) && instancesRunning.remove(issueKey)) {
            runtimeService.createMessageCorrelation(ProcessConstants.MSG_NAME_ISSUE_UPDATED_ + issueKey)
                    .setVariable(ProcessConstants.VAR_NAME_ISSUE_CLOSED, true)
                    .correlate();
        }
    }

    public void finishProcessInstance(String issueKey) {
        if (instancesRunning.remove(issueKey)) {
            runtimeService.createMessageCorrelation(ProcessConstants.MSG_NAME_CONFIG_DEPLOYED_ + issueKey)
                    .correlate();
        }
    }

    public void addWaiting(String issueKey) {
        instancesWaiting.add(issueKey);
    }
}
