package com.IhorZaiets.githubAPI.modules.github;

import com.IhorZaiets.githubAPI.exceptions.ExceptionMessage;
import com.IhorZaiets.githubAPI.exceptions.ResourceNotFoundException;
import com.IhorZaiets.githubAPI.modules.github.dto.RepositoryBranchDTO;
import com.IhorZaiets.githubAPI.modules.github.dto.RepositoryInfoDTO;
import com.IhorZaiets.githubAPI.modules.github.dto.RepositoryInfoRequestDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class GitHubService {

    public static final String REPOSITORY_PROPERTY_NAME_NAME = "name";
    public static final String REPOSITORY_PROPERTY_NAME_OWNER = "owner";
    public static final String REPOSITORY_PROPERTY_NAME_IS_FORK = "fork";
    public static final String OWNER_PROPERTY_NAME_USERNAME = "login";
    public static final String BRANCH_PROPERTY_NAME_NAME = "name";
    public static final String BRANCH_PROPERTY_NAME_LAST_COMMIT = "commit";
    public static final String LAST_COMMIT_PROPERTY_NAME_SHA = "sha";

    private static String GITHUB_API_URL = "https://api.github.com";

    public static String getGithubApiUrl() {
        return GITHUB_API_URL;
    }

    public static void setGithubApiUrl(String githubApiUrl) {
        GITHUB_API_URL = githubApiUrl;
    }

    public List<RepositoryInfoDTO> getUserRepositoriesInfo(RepositoryInfoRequestDTO repositoryInfoRequestDTO) {
        validateRequest(repositoryInfoRequestDTO);
        List<RepositoryInfoDTO> repositoryInfoDTOS = new ArrayList<>();
        List<Map<String, Object>> repositoriesListResponse = getRepositoriesFromGitHub(repositoryInfoRequestDTO);
        for (Map<String, Object> repository : repositoriesListResponse) {
            if (!(boolean) repository.get(REPOSITORY_PROPERTY_NAME_IS_FORK)) {
                RepositoryInfoDTO repositoryInfoDTO = new RepositoryInfoDTO();
                List<RepositoryBranchDTO> repositoryBranchDTOS = new ArrayList<>();
                String repositoryName = repository.get(REPOSITORY_PROPERTY_NAME_NAME).toString();
                LinkedHashMap<String, Object> owner = (LinkedHashMap<String, Object>) repository.get(REPOSITORY_PROPERTY_NAME_OWNER);
                List<Map<String, Object>> branchesListResponse = getBranchesForRepositoryFromGitHub(repositoryInfoRequestDTO, repositoryName);
                for (Map<String, Object> branchResponse : branchesListResponse) {
                    RepositoryBranchDTO repositoryBranchDTO = new RepositoryBranchDTO();
                    LinkedHashMap<String, Object> commit = (LinkedHashMap<String, Object>) branchResponse.get(BRANCH_PROPERTY_NAME_LAST_COMMIT);
                    repositoryBranchDTO.setName(branchResponse.get(BRANCH_PROPERTY_NAME_NAME).toString());
                    repositoryBranchDTO.setLastCommitSHA(commit.get(LAST_COMMIT_PROPERTY_NAME_SHA).toString());
                    repositoryBranchDTOS.add(repositoryBranchDTO);
                }
                repositoryInfoDTO.setName(repositoryName);
                repositoryInfoDTO.setOwnerUsername(owner.get(OWNER_PROPERTY_NAME_USERNAME).toString());
                repositoryInfoDTO.setRepositoryBranchList(repositoryBranchDTOS);
                repositoryInfoDTOS.add(repositoryInfoDTO);
            }
        }
        return repositoryInfoDTOS;
    }

    private void validateRequest(RepositoryInfoRequestDTO repositoryInfoRequestDTO) {
        WebClient webClient = WebClient.create(GITHUB_API_URL + "/users/" + repositoryInfoRequestDTO.getUsername());
        webClient.get()
                .retrieve()
                .onStatus(statusCode -> statusCode.equals(HttpStatus.NOT_FOUND), response -> Mono.error(new ResourceNotFoundException(ExceptionMessage.USER_NOT_FOUND)))
                .onStatus(HttpStatusCode::isError, response -> Mono.error(new RuntimeException(ExceptionMessage.CONTACT_ADMINISTRATOR)))
                .bodyToMono(String.class)
                .block();
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
