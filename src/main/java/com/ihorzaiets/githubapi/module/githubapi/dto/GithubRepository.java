package com.ihorzaiets.githubapi.module.githubapi.dto;

import java.util.List;

public record GithubRepository(String name, String ownerLogin, boolean isFork, List<RepositoryBranch> branches) {}
