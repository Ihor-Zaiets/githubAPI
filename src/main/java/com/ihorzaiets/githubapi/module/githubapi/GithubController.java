package com.ihorzaiets.githubapi.module.githubapi;

import com.ihorzaiets.githubapi.module.githubapi.dto.GithubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/github")
public class GithubController {

    @Autowired
    private GithubService githubService;

    @GetMapping("/getUserRepositoriesInfo/{username}")
    public ResponseEntity<List<GithubRepository>> getUserRepositoriesInfo(@PathVariable String username) {
        return ResponseEntity.ok(githubService.getUserRepositoriesInfo(username));
    }
}
