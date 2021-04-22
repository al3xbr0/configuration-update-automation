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

import java.util.List;
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
        return issueRestClient.getIssue(issueKey).claim();
    }

    public void setIssueToDo(String issueKey) {
        LOGGER.info("Setting \"To Do\" status for issue {}", issueKey);
        Issue issue = getIssue(issueKey);
        issueRestClient.transition(issue, mapTransition(issue, List.of("to do", "open", "reopened"))).claim();
    }

    public void setIssueInProgress(String issueKey) {
        LOGGER.info("Setting \"In Progress\" status for issue {}", issueKey);
        Issue issue = getIssue(issueKey);
        issueRestClient.transition(issue, mapTransition(issue, List.of("in progress"))).claim();
    }

    public void setIssueInReview(String issueKey) {
        LOGGER.info("Setting \"In Review\" status for issue {}", issueKey);
        Issue issue = getIssue(issueKey);
        issueRestClient.transition(issue, mapTransition(issue, List.of("in review"))).claim();
    }

    public void setIssueClosed(String issueKey) {
        LOGGER.info("Setting \"Done\" status for issue {}", issueKey);
        Issue issue = getIssue(issueKey);
        issueRestClient.transition(issue, mapTransition(issue, List.of("done", "closed", "resolved"))).claim();
    }

    private TransitionInput mapTransition(Issue issue, List<String> transitions) {
        issueRestClient.getTransitions(issue).claim().iterator();
        Transition transition =
                StreamSupport.stream(issueRestClient.getTransitions(issue).claim().spliterator(), false)
                        .filter(
                                t -> transitions.contains(t.getName().toLowerCase())
                        ).findFirst()
                        .orElseThrow(() -> new UnsupportedOperationException("Couldn't get transition"));
        return new TransitionInput(transition.getId());
    }
}
