package ru.w.automation.service;

import org.gitlab4j.api.CommitsApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.CommitAction;
import org.gitlab4j.api.models.CommitPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GitLabIntegrationService {

    @Autowired
    private CommitsApi commitsApi;
    @Value("")
    private String url;

    public void add() throws GitLabApiException {
        commitsApi.createCommit("", new CommitPayload().withBranch("br").withStartBranch("master")
                .withCommitMessage("msg").withAction(new CommitAction().withAction(CommitAction.Action.CREATE)
                .withFilePath("").withContent("json")));
    }
}
