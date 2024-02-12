package com.IhorZaiets.githubAPI.modules.github;

import com.IhorZaiets.githubAPI.modules.github.dto.RepositoryInfoRequestDTO;
import com.IhorZaiets.githubAPI.modules.github.dto.RepositoryInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/github")
public class GitHubController {

    @Autowired
    private GitHubService gitHubService;

    @PostMapping(value = "/getUserRepInfo")
    public ResponseEntity<List<RepositoryInfoDTO>> getUserRepositoriesInfo(@RequestBody RepositoryInfoRequestDTO repositoryInfoRequestDTO) {
        return ResponseEntity.ok(gitHubService.getUserRepositoriesInfo(repositoryInfoRequestDTO));
    }
}
