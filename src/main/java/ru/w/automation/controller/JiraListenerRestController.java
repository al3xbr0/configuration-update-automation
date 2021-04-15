package ru.w.automation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.w.automation.service.ProcessInstanceService;

@SuppressWarnings("unused")
@RestController
public class JiraListenerRestController {

    @Autowired
    private ProcessInstanceService processInstanceService;

    @PostMapping("api/jira/create/{issueKey:[a-zA-Z]+-\\d+}")
    public void created(@PathVariable("issueKey") String issueKey) {
        processInstanceService.startProcessInstance(issueKey);
    }

    @PostMapping("api/jira/update/{issueKey:[a-zA-Z]+-\\d+}")
    public void updated(@PathVariable("issueKey") String issueKey) {
        processInstanceService.updateProcessInstance(issueKey);
    }

    @PostMapping("api/jira/canceled/{issueKey:[a-zA-Z]+-\\d+}")
    public void canceled(@PathVariable("issueKey") String issueKey) {
        processInstanceService.continueProcessInstance(issueKey);
    }

    @PostMapping("api/jira/close/{issueKey:[a-zA-Z]+-\\d+}")
    public void commentedWith(@PathVariable("issueKey") String issueKey) {
        processInstanceService.finishProcessInstance(issueKey);
    }
}
