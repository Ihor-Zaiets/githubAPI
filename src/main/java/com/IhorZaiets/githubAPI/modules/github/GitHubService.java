package com.IhorZaiets.githubAPI.modules.github;

import com.IhorZaiets.githubAPI.modules.github.dto.RepositoryBranchDTO;
import com.IhorZaiets.githubAPI.modules.github.dto.RepositoryInfoRequestDTO;
import com.IhorZaiets.githubAPI.modules.github.dto.RepositoryInfoDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class GitHubService {

    public static final String REPOSITORY_NAME_PROPERTY_NAME = "name";
    public static final String REPOSITORY_OWNER_PROPERTY_NAME = "owner";
    public static final String OWNER_USERNAME_PROPERTY_NAME = "login";
    public static final String BRANCH_NAME_PROPERTY_NAME = "name";
    public static final String BRANCH_LAST_COMMIT_PROPERTY_NAME = "commit";
    public static final String LAST_COMMIT_SHA_PROPERTY_NAME = "sha";

    public static final String GITHUB_API_URL = "https://api.github.com";

    public List<RepositoryInfoDTO> getUserRepositoriesInfo(RepositoryInfoRequestDTO repositoryInfoRequestDTO) {
        List<RepositoryInfoDTO> repositoryInfoDTOS = new ArrayList<>();
        List<Map<String, Object>> repositoriesListResponse = getRepositoriesFromGitHub(repositoryInfoRequestDTO);
        for (Map<String, Object> repository : repositoriesListResponse) {
            RepositoryInfoDTO repositoryInfoDTO = new RepositoryInfoDTO();
            List<RepositoryBranchDTO> repositoryBranchDTOS = new ArrayList<>();
            String repositoryName = repository.get(REPOSITORY_NAME_PROPERTY_NAME).toString();
            LinkedHashMap<String, Object> owner = (LinkedHashMap<String, Object>) repository.get(REPOSITORY_OWNER_PROPERTY_NAME);
            List<Map<String, Object>> branchesListResponse = getBranchesForRepositoryFromGitHub(repositoryInfoRequestDTO, repositoryName);
            for (Map<String, Object> branchResponse : branchesListResponse) {
                RepositoryBranchDTO repositoryBranchDTO = new RepositoryBranchDTO();
                LinkedHashMap<String, Object> commit = (LinkedHashMap<String, Object>) branchResponse.get(BRANCH_LAST_COMMIT_PROPERTY_NAME);
                repositoryBranchDTO.setName(branchResponse.get(BRANCH_NAME_PROPERTY_NAME).toString());
                repositoryBranchDTO.setLastCommitSHA(commit.get(LAST_COMMIT_SHA_PROPERTY_NAME).toString());
                repositoryBranchDTOS.add(repositoryBranchDTO);
            }
            repositoryInfoDTO.setName(repositoryName);
            repositoryInfoDTO.setOwnerUsername(owner.get(OWNER_USERNAME_PROPERTY_NAME).toString());
            repositoryInfoDTO.setRepositoryBranchList(repositoryBranchDTOS);
            repositoryInfoDTOS.add(repositoryInfoDTO);
        }
        return repositoryInfoDTOS;
    }

    private static List<Map<String, Object>> getBranchesForRepositoryFromGitHub(RepositoryInfoRequestDTO repositoryInfoRequestDTO, String repositoryName) {
        WebClient branchClient = WebClient.create(GITHUB_API_URL + String.format("/repos/%s/%s/branches", repositoryInfoRequestDTO.getUsername(), repositoryName));
        Mono<List<Map<String, Object>>> monoBranchesResponse = branchClient.get().retrieve().bodyToMono(new ParameterizedTypeReference<>() {});
        return monoBranchesResponse.block();
    }

    private static List<Map<String, Object>> getRepositoriesFromGitHub(RepositoryInfoRequestDTO repositoryInfoRequestDTO) {
        WebClient repoClient = WebClient.create(GITHUB_API_URL + String.format("/users/%s/repos", repositoryInfoRequestDTO.getUsername()));
        Mono<List<Map<String, Object>>> monoRepoResponse = repoClient.get().retrieve().bodyToMono(new ParameterizedTypeReference<>() {});
        return monoRepoResponse.block();
    }
}
