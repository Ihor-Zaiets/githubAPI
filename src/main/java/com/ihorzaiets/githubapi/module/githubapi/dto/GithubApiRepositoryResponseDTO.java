package com.ihorzaiets.githubapi.module.githubapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GithubApiRepositoryResponseDTO(
        String name,
        Owner owner,
        @JsonProperty("fork") boolean isFork
) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Owner(String login) {}
}
