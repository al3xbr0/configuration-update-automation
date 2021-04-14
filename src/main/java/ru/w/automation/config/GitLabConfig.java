package ru.w.automation.config;


import org.gitlab4j.api.CommitsApi;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.MergeRequestApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GitLabConfig {

    @Autowired
    private GitLabApi gitLabApi;

    @Value("${gitlab.repo-url}")
    private String repoUrl;
    @Value("${gitlab.access-token}")
    private String accessToken;

    @Bean
    public GitLabApi gitlabAPI() {
        return new GitLabApi(repoUrl, accessToken);
    }

    @Bean
    public CommitsApi commitsApi(){
        return gitLabApi.getCommitsApi();
    }

    @Bean
    public MergeRequestApi mergeRequestApi(){
        return gitLabApi.getMergeRequestApi();
    }
}
