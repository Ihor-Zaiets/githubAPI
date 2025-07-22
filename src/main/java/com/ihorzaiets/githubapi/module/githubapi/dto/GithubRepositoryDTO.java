package com.ihorzaiets.githubapi.module.githubapi.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GithubRepositoryDTO(String name, String ownerLogin, boolean isFork, List<GithubRepositoryBranchDTO> branches) {

    @JsonCreator
    public GithubRepositoryDTO(
            @JsonProperty("name") String name,
            @JsonProperty("owner") Owner owner,
            @JsonProperty("fork") boolean isFork) {
        this(name, owner.login(), isFork, null);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Owner(String login) {}
}
