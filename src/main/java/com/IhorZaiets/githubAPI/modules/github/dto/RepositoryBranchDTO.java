package com.IhorZaiets.githubAPI.modules.github.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RepositoryBranchDTO {
    private String name;
    private String lastCommitSHA;
}
