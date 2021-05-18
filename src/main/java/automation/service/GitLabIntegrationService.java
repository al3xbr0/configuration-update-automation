package automation.service;

import automation.domain.GitLabProperties;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.CommitAction;
import org.gitlab4j.api.models.CommitPayload;
import org.gitlab4j.api.models.MergeRequest;
import org.gitlab4j.api.models.MergeRequestParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GitLabIntegrationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GitLabIntegrationService.class);

    @Autowired
    private GitLabApi gitLabApi;
    @Autowired
    private GitLabProperties gitLabProperties;

    public void commitFile(String branchName, String commitMessage, String fileName, String fileContent) throws GitLabApiException {
        LOGGER.info("Committing file '{}' to branch '{}'", fileName, branchName);
        boolean fileExists;
        try {
            gitLabApi.getRepositoryFileApi().getFile(gitLabProperties.getRepoPath(), fileName, gitLabProperties.getStartBranch());
            fileExists = true;
            LOGGER.info("File '{}' already exists and will be updated", fileName);
        } catch (GitLabApiException e) {
            if (e.getHttpStatus() == 404) {
                LOGGER.info("File '{}' doesn't exist and will be created", fileName);
                fileExists = false;
            } else {
                throw e;
            }
        }
        CommitAction commitAction = new CommitAction()
                .withFilePath(fileName)
                .withContent(fileContent)
                .withAction(fileExists ? CommitAction.Action.UPDATE : CommitAction.Action.CREATE);

        gitLabApi.getCommitsApi().createCommit(
                gitLabProperties.getRepoPath(),
                new CommitPayload()
                        .withBranch(branchName)
                        .withStartBranch(gitLabProperties.getStartBranch())
                        .withCommitMessage(commitMessage)
                        .withAction(commitAction)
        );
    }

    public MergeRequest createMergeRequest(String sourceBranch) throws GitLabApiException {
        LOGGER.info("Creating merge request for branch '{}'", sourceBranch);
        String title = sourceBranch.toUpperCase();
        return gitLabApi.getMergeRequestApi().createMergeRequest(
                gitLabProperties.getRepoPath(),
                new MergeRequestParams()
                        .withSourceBranch(sourceBranch)
                        .withTargetBranch(gitLabProperties.getStartBranch())
                        .withTitle(title)
                        .withRemoveSourceBranch(true)
        );
    }

    public boolean checkIfMerged(int mergeRequestIid) throws GitLabApiException {
        return "merged".equalsIgnoreCase(
                gitLabApi.getMergeRequestApi().getMergeRequest(gitLabProperties.getRepoPath(), mergeRequestIid).getState()
        );
    }

    public boolean checkIfOpen(int mergeRequestIid) throws GitLabApiException {
        return "opened".equalsIgnoreCase(
                gitLabApi.getMergeRequestApi().getMergeRequest(gitLabProperties.getRepoPath(), mergeRequestIid).getState()
        );
    }

    public static String convertStringToBranchName(String s) {
        //https://wincent.com/wiki/Legal_Git_branch_names
        return s.replaceAll("\\s", "_")
                .replaceAll("^\\.|\\.\\.|\\\\|[\\000-\\037\\0177*~^:?\\[]|.lock$|@\\{", "")
                .toLowerCase();
    }
}
