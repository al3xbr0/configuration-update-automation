package ru.w.automation.controller;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IssueCreatedController {

    @Autowired
    private RuntimeService runtimeService;

    @PostMapping(value = "api/jira/create/{key}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void created(@PathVariable String issueKey) {
        runtimeService.createProcessInstanceById("").setVariable("issueKey", issueKey).execute();

    }
}
