package com.IhorZaiets.githubAPI.modules.github;

import com.IhorZaiets.githubAPI.modules.github.dto.RepositoryInfoRequestDTO;
import com.IhorZaiets.githubAPI.modules.github.dto.RepositoryInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/github")
public class GitHubController {

    @Autowired
    private GitHubService gitHubService;

    @GetMapping(value = "/getUserRepInfo", consumes = "application/json")
    public ResponseEntity<List<RepositoryInfoDTO>> getUserRepositoriesInfo(RepositoryInfoRequestDTO repositoryInfoRequestDTO) {
        return ResponseEntity.ok(gitHubService.getUserRepositoriesInfo(repositoryInfoRequestDTO));
    }
}
