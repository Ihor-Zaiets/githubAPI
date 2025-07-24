package com.ihorzaiets.githubapi.module.githubapi.dto;

import java.util.List;

public record GithubRepositoryDTO(String name, String ownerLogin, List<GithubRepositoryBranchDTO> branches) {}
