package com.IhorZaiets.githubAPI.modules.github;

import com.IhorZaiets.githubAPI.modules.github.dto.RepositoryInfoRequestDTO;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
public class GitHubServiceUnitTest {

    @InjectMocks
    private GitHubService gitHubService;

    @Test
    public void shouldReturnRepInfoWhenGivenUsername() {
        RepositoryInfoRequestDTO repositoryInfoRequestDTO = mock();
        assertFalse(gitHubService.getUserRepositoriesInfo(repositoryInfoRequestDTO).isEmpty());
    }
}
