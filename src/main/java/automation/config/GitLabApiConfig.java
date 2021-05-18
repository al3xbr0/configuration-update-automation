package automation.config;

import automation.domain.GitLabProperties;
import org.gitlab4j.api.GitLabApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GitLabApiConfig {

    @Value("${gitlab.host-url}")
    private String hostUrl;
    @Value("${gitlab.access-token}")
    private String accessToken;
    @Value("${gitlab.repo-path}")
    private String repoPath;
    @Value("${gitlab.start-branch}")
    private String startBranch;

    @Bean
    public GitLabProperties gitLabProperties() {
        return new GitLabProperties(hostUrl, accessToken, repoPath, startBranch);
    }

    @Bean
    public GitLabApi gitLabApi() {
        return new GitLabApi(gitLabProperties().getHostUrl(), gitLabProperties().getAccessToken());
    }
}
