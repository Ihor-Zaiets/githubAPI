package com.IhorZaiets.githubAPI.modules.github;

import com.IhorZaiets.githubAPI.modules.github.dto.RepositoryBranchDTO;
import com.IhorZaiets.githubAPI.modules.github.dto.RepositoryInfoDTO;
import com.IhorZaiets.githubAPI.modules.github.dto.RepositoryInfoRequestDTO;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

@Tag("unit")
@ExtendWith(MockitoExtension.class)
public class GitHubServiceUnitTest {

    @InjectMocks
    private GitHubService gitHubService;

    public void shouldReturnRepInfoWhenGivenUsername() {
        RepositoryInfoRequestDTO repositoryInfoRequestDTO = mock();
        assertFalse(gitHubService.getUserRepositoriesInfo(repositoryInfoRequestDTO).isEmpty());
    }

    @Test
    public void apiTesting() {
        assertDoesNotThrow(() -> {
            WebClient repoClient = WebClient.create("https://api.github.com/users/Ihor-Zaiets/repos");
            Mono<List<Map<String, Object>>> monoRepoResponse = repoClient.get().retrieve().bodyToMono(new ParameterizedTypeReference<>() {});
            List<Map<String, Object>> repositoriesListResponse = monoRepoResponse.block();

            WebClient branchClient = WebClient.create("https://api.github.com/repos/Ihor-Zaiets/booking_university_project/branches");
            Mono<List<Map<String, Object>>> monoBranchesResponse = branchClient.get().retrieve().bodyToMono(new ParameterizedTypeReference<>() {});
            List<Map<String, Object>> branchesListResponse = monoBranchesResponse.block();
            List<RepositoryInfoDTO> repositoryInfoDTOS = new ArrayList<>();
            for (Map<String, Object> repository : repositoriesListResponse) {
                RepositoryInfoDTO repositoryInfoDTO = new RepositoryInfoDTO();
                repositoryInfoDTO.setName(repository.get("name").toString());
                LinkedHashMap<String, Object> owner = (LinkedHashMap<String, Object>) repository.get("owner");
                repositoryInfoDTO.setOwnerUsername(owner.get("login").toString());
                List<RepositoryBranchDTO> repositoryBranchDTOS = new ArrayList<>();
                for (Map<String, Object> branchResponse : branchesListResponse) {
                    RepositoryBranchDTO repositoryBranchDTO = new RepositoryBranchDTO();
                    repositoryBranchDTO.setName(branchResponse.get("name").toString());
                    LinkedHashMap<String, Object> commit = (LinkedHashMap<String, Object>) branchResponse.get("commit");
                    repositoryBranchDTO.setLastCommitSHA(commit.get("sha").toString());
                    repositoryBranchDTOS.add(repositoryBranchDTO);
                }
                repositoryInfoDTO.setRepositoryBranchList(repositoryBranchDTOS);
                repositoryInfoDTOS.add(repositoryInfoDTO);
            }
            System.out.println(repositoryInfoDTOS);
        });
    }
}
