package ru.w.automation.config;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.MetadataRestClient;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
public class JiraRestClientConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(JiraRestClientConfig.class);

    @Value("${jira.url}")
    private String url;
    @Value("${jira.username}")
    private String username;
    @Value("${jira.api-key}")
    private String apiKey;

    @Bean
    public IssueRestClient issueRestClient() {
        LOGGER.info("Initializing Jira Issue Client");
        return jiraRestClient().getIssueClient();
    }

    @Bean
    public MetadataRestClient metadataRestClient() {
        LOGGER.info("Initializing Jira Metadata Client");
        return jiraRestClient().getMetadataClient();
    }

    private JiraRestClient jiraRestClient() {
        LOGGER.info("Initializing Jira Rest Client for {}, username: {}, API key: {}", url, username, apiKey);
        return new AsynchronousJiraRestClientFactory()
                .createWithBasicHttpAuthentication(URI.create(url), username, apiKey);
    }
}
