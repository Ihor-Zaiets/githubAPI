package com.ihorzaiets.githubapi.module.githubapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GithubApiBranchResponseDTO(String name, Commit commit) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Commit(String sha){}
}
