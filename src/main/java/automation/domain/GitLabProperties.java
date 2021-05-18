package automation.domain;

public class GitLabProperties {
    private final String hostUrl;
    private final String accessToken;
    private final String repoPath;
    private final String startBranch;

    public GitLabProperties(String hostUrl, String accessToken, String repoPath, String startBranch) {
        this.hostUrl = hostUrl;
        this.accessToken = accessToken;
        this.repoPath = repoPath;
        this.startBranch = startBranch;
    }

    public String getHostUrl() {
        return hostUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRepoPath() {
        return repoPath;
    }

    public String getStartBranch() {
        return startBranch;
    }
}
