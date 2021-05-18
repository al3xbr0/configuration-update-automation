package automation.controller;

import automation.service.ProcessInstanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SuppressWarnings("unused")
public class JiraListenerRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(JiraListenerRestController.class);

    @Autowired
    private ProcessInstanceService processInstanceService;

    @PostMapping("api/jira/created/{issueKey:[a-zA-Z0-9]+-\\d+}")
    public void created(@PathVariable("issueKey") String issueKey) {
        LOGGER.info("Requested POST /api/jira/created/{}", issueKey);
        processInstanceService.startProcessInstance(issueKey);
    }

    @PostMapping("api/jira/updated/{issueKey:[a-zA-Z0-9]+-\\d+}")
    public void updated(@PathVariable("issueKey") String issueKey) {
        LOGGER.info("Requested POST /api/jira/updated/{}", issueKey);
        processInstanceService.updateProcessInstance(issueKey);
    }

    @PostMapping("api/jira/canceled/{issueKey:[a-zA-Z0-9]+-\\d+}")
    public void canceled(@PathVariable("issueKey") String issueKey) {
        LOGGER.info("Requested POST /api/jira/canceled/{}", issueKey);
        processInstanceService.cancelProcessInstance(issueKey);
    }

    @PostMapping("api/jira/finished/{issueKey:[a-zA-Z0-9]+-\\d+}")
    public void finished(@PathVariable("issueKey") String issueKey) {
        LOGGER.info("Requested POST /api/jira/finished/{}", issueKey);
        processInstanceService.finishProcessInstance(issueKey);
    }
}
