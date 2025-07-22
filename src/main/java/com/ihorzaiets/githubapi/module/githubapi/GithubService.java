package com.ihorzaiets.githubapi.module.githubapi;

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
            List<GithubRepositoryDTO> userRepositoriesWithBranches = new ArrayList<>();
            List<GithubRepositoryDTO> userRepositories = githubClient.getUserRepositories(username);
            for (GithubRepositoryDTO userRepository : userRepositories) {
                if (!userRepository.isFork()) {
                    List<GithubRepositoryBranchDTO> userRepositoryBranches = githubClient.getUserRepositoryBranches(username, userRepository.name());
                    userRepositoriesWithBranches.add(new GithubRepositoryDTO(userRepository.name(), userRepository.ownerLogin(), userRepository.isFork(), userRepositoryBranches));
                }
            }
            return userRepositoriesWithBranches;
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
