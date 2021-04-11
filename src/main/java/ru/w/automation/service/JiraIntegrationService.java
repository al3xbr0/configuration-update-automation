package ru.w.automation.service;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.MetadataRestClient;
import com.atlassian.jira.rest.client.api.domain.Comment;
import com.atlassian.jira.rest.client.api.domain.Field;
import com.atlassian.jira.rest.client.api.domain.Issue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JiraIntegrationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JiraIntegrationService.class);

//    @Autowired
//    private JiraRestClient jiraRestClient;

    @Autowired
    private IssueRestClient issueRestClient;
    @Autowired
    private MetadataRestClient metadataRestClient;

    public void addComment(String issueKey, String commentText) {
        Issue issue = getIssue(issueKey);
        LOGGER.info("Sending comment to issue {}", issueKey);
        issueRestClient.addComment(issue.getCommentsUri(), Comment.valueOf(commentText));
    }

    public Issue getIssue(String issueKey) {
        LOGGER.info("Looking for issue {}", issueKey);
        Issue issue = issueRestClient.getIssue(issueKey).claim();
        LOGGER.info("Issue {} found", issueKey);
        return issue;
    }

    public Iterable<Field> getFields() {
        return metadataRestClient.getFields().claim();
    }

}
