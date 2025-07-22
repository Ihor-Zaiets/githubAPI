package com.ihorzaiets.githubapi.module.githubapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ihorzaiets.githubapi.module.githubapi.dto.GithubRepositoryBranchDTO;
import com.ihorzaiets.githubapi.module.githubapi.dto.GithubRepositoryDTO;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Component
public class GithubClient {

    public static final String GITHUB_URL = "https://api.github.com";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public List<GithubRepositoryDTO> getUserRepositories(String username) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("%s/users/%s/repos".formatted(GITHUB_URL, username)))
                .header("Accept", "application/vnd.github+json")
                .header("User-Agent", "JavaHttpClient")
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());


        return mapper.readValue(
                response.body(),
                mapper.getTypeFactory().constructCollectionType(List.class, GithubRepositoryDTO.class)
        );
    }

    public List<GithubRepositoryBranchDTO> getUserRepositoryBranches(String username, String repositoryName) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("%s/repos/%s/%s/branches".formatted(GITHUB_URL, username, repositoryName)))
                .header("Accept", "application/vnd.github+json")
                .header("User-Agent", "JavaHttpClient")
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return mapper.readValue(
                response.body(),
                mapper.getTypeFactory().constructCollectionType(List.class, GithubRepositoryBranchDTO.class)
        );
    }
}
