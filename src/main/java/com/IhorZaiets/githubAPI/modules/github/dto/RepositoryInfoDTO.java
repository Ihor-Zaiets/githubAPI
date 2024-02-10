package com.IhorZaiets.githubAPI.modules.github.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RepositoryInfoDTO {
    private String name;
    private String ownerUsername;
    private List<RepositoryBranchDTO> repositoryBranchList;
}
