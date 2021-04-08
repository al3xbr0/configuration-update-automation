package ru.w.automation.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IssueCreatedController {

    @PostMapping(value = "api/jira/create/{key}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void created(@PathVariable String issueKey) {
        //from here the process starts

    }
}
