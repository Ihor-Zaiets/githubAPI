package com.IhorZaiets.githubAPI.modules.github;

import com.IhorZaiets.githubAPI.modules.github.dto.RepositoryBranchDTO;
import com.IhorZaiets.githubAPI.modules.github.dto.RepositoryInfoDTO;
import com.IhorZaiets.githubAPI.modules.github.dto.RepositoryInfoRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GitHubServiceIntegrationTest {

    @Autowired
    private GitHubService gitHubService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public static MockWebServer mockWebServer;

    private static final String TEST_REPO_NAME = "test_repo_name";
    private static final String TEST_OWNER_USERNAME = "ownerUsername";
    private static final String TEST_BRANCH_NAME = "branch";
    private static final String TEST_LAST_COMMIT_SHA = "1b29061adecf0bebf4ca015b20b83a7b56cec42f";
    private static final String HEADER_NAME_CONTENT_TYPE = "Content-Type";
    private static final String HEADER_VALUE_CONTENT_TYPE_JSON = "application/json";

    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @BeforeEach
    void initialize() {
        String baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
        GitHubService.setGithubApiUrl(baseUrl);
    }

    @Test
    public void shouldReturnRepInfoWhenGivenUsername() throws JsonProcessingException {
        RepositoryBranchDTO repositoryBranchDTO = new RepositoryBranchDTO();
        repositoryBranchDTO.setName(TEST_BRANCH_NAME);
        repositoryBranchDTO.setLastCommitSHA(TEST_LAST_COMMIT_SHA);
        RepositoryInfoDTO repositoryInfoDTO = new RepositoryInfoDTO();
        repositoryInfoDTO.setName(TEST_REPO_NAME);
        repositoryInfoDTO.setOwnerUsername(TEST_OWNER_USERNAME);
        repositoryInfoDTO.setRepositoryBranchList(List.of(repositoryBranchDTO));

        mockWebServer.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(repositoryInfoDTO))
                .addHeader(HEADER_NAME_CONTENT_TYPE, HEADER_VALUE_CONTENT_TYPE_JSON));

        RepositoryInfoRequestDTO repositoryInfoRequestDTO = new RepositoryInfoRequestDTO();
        repositoryInfoRequestDTO.setUsername(TEST_OWNER_USERNAME);
        List<RepositoryInfoDTO> userRepositoriesInfo = gitHubService.getUserRepositoriesInfo(repositoryInfoRequestDTO);

        assertEquals(1, userRepositoriesInfo.size());
        RepositoryInfoDTO repositoryInfo = userRepositoriesInfo.get(0);
        assertEquals(1, repositoryInfo.getRepositoryBranchList().size());
        assertEquals(TEST_REPO_NAME, repositoryInfo.getName());
        assertEquals(TEST_OWNER_USERNAME, repositoryInfo.getOwnerUsername());
        assertEquals(TEST_BRANCH_NAME, repositoryInfo.getRepositoryBranchList().get(0).getName());
        assertEquals(TEST_LAST_COMMIT_SHA, repositoryInfo.getRepositoryBranchList().get(0).getLastCommitSHA());
    }
}
