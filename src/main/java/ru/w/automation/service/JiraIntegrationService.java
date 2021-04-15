package ru.w.automation.service;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.domain.Comment;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.Transition;
import com.atlassian.jira.rest.client.api.domain.input.TransitionInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.StreamSupport;

@Service
public class JiraIntegrationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(JiraIntegrationService.class);

    @Autowired
    private IssueRestClient issueRestClient;

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

    public void closeIssue(String issueKey) {
        LOGGER.info("Closing issue {}", issueKey);
        Issue issue = getIssue(issueKey);
        issueRestClient.getTransitions(issue).claim().iterator();
        Transition transition =
                StreamSupport.stream(issueRestClient.getTransitions(issue).claim().spliterator(), false)
                        .filter(
                                t -> "done".equalsIgnoreCase(t.getName())
                                        || "closed".equalsIgnoreCase(t.getName())
                                        || "resolved".equalsIgnoreCase(t.getName())
                        ).findFirst()
                        .orElseThrow(() -> new UnsupportedOperationException("Couldn't get transition for closing the issue"));

        issueRestClient.transition(issue, new TransitionInput(transition.getId())).claim();
    }
}
