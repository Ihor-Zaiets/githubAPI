package com.ihorzaiets.githubapi.module.githubapi.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GithubRepositoryBranchDTO(String name, String sha) {

    @JsonCreator
    public GithubRepositoryBranchDTO(
            @JsonProperty("name") String name,
            @JsonProperty("commit") Commit commit) {
        this(name, commit.sha());
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Commit(String sha) {}
}
