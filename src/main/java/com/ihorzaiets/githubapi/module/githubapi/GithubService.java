package com.ihorzaiets.githubapi.module.githubapi;

import com.ihorzaiets.githubapi.module.githubapi.dto.GithubApiRepositoryResponseDTO;
import com.ihorzaiets.githubapi.module.githubapi.dto.GithubRepositoryBranchDTO;
import com.ihorzaiets.githubapi.module.githubapi.dto.GithubRepositoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GithubService {

    @Autowired
    private GithubClient githubClient;

    public List<GithubRepositoryDTO> getUserRepositoriesInfo(String username) {
        try {
            List<GithubRepositoryDTO> result = new ArrayList<>();
            List<GithubApiRepositoryResponseDTO> githubRepositoriesResponse = githubClient.getUserRepositories(username);
            for (GithubApiRepositoryResponseDTO githubRepositoryResponse : githubRepositoriesResponse) {
                if (!githubRepositoryResponse.isFork()) {
                    List<GithubRepositoryBranchDTO> userRepositoryBranches =
                            githubClient.getUserRepositoryBranches(username, githubRepositoryResponse.name()).stream()
                                    .map(githubBranch -> new GithubRepositoryBranchDTO(githubBranch.name(), githubBranch.commit().sha()))
                                    .collect(Collectors.toList());
                    result.add(new GithubRepositoryDTO(githubRepositoryResponse.name(), githubRepositoryResponse.owner().login(), userRepositoryBranches));
                }
            }
            return result;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
