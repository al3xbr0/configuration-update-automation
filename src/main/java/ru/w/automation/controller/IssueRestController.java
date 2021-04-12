package ru.w.automation.controller;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IssueRestController {

    @Autowired
    private RuntimeService runtimeService;

    @PostMapping(value = "api/jira/create/{issueKey:[a-zA-Z]+-\\d+}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@PathVariable("issueKey") String issueKey) {
        runtimeService.createProcessInstanceById("").setVariable("issueKey", issueKey).execute();
    }

    @PostMapping(value = "api/jira/update/{issueKey:[a-zA-Z]+-\\d+}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@PathVariable("issueKey") String issueKey) {
        //TODO
    }
}
