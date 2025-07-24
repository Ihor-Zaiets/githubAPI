package com.ihorzaiets.githubapi;

import com.ihorzaiets.githubapi.module.githubapi.dto.GithubRepositoryBranchDTO;
import com.ihorzaiets.githubapi.module.githubapi.dto.GithubRepositoryDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GithubApiApplicationTests {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	void applicationTest() {
		//given
		String testUsername = "octocat";

		//when
		ResponseEntity<List<GithubRepositoryDTO>> response = testRestTemplate.exchange(
				"/api/github/getUserRepositoriesInfo/%s".formatted(testUsername),
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<>(){});

		//then
		List<GithubRepositoryDTO> repositories = response.getBody();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(repositories);

		for (GithubRepositoryDTO repo : repositories) {
			assertNotNull(repo.name());
			assertNotNull(repo.ownerLogin());
			assertNotNull(repo.branches());

			assertFalse(repo.name().isBlank());
			assertFalse(repo.ownerLogin().isBlank());
			assertFalse(repo.branches().isEmpty());

			assertEquals(repo.ownerLogin(), testUsername);

			for (GithubRepositoryBranchDTO branch : repo.branches()) {
				assertNotNull(branch.name());
				assertNotNull(branch.sha());

				assertFalse(branch.name().isBlank());
				assertFalse(branch.sha().isBlank());
			}
		}
	}
}
