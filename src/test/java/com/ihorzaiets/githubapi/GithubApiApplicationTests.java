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
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GithubApiApplicationTests {

	@Autowired
	private TestRestTemplate testRestTemplate;

	/**
	 * The test is written in a way to not have any mocks, therefore the call to GitHub api is not mocked.
	 * <p>
	 * The test relays on data from GitHub's test user "octocat". So, if any changed would happen with GitHub test data, test may stop working properly.
	 * */
	@Test
	void applicationTest() {
		//given
		final String TEST_USERNAME = "octocat";
		final String FORKED_REPOSITORY_NAME = "boysenberry-repo-1";

		//when
		ResponseEntity<List<GithubRepositoryDTO>> response = testRestTemplate.exchange(
				"/api/github/getUserRepositoriesInfo/%s".formatted(TEST_USERNAME),
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<>(){});

		//then
		List<GithubRepositoryDTO> repositories = response.getBody();
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(repositories);

		Set<String> repoNameSet = repositories.stream().map(GithubRepositoryDTO::name).collect(Collectors.toSet());
		assertFalse(repoNameSet.contains(FORKED_REPOSITORY_NAME));

		for (GithubRepositoryDTO repo : repositories) {
			assertNotNull(repo.name());
			assertNotNull(repo.ownerLogin());
			assertNotNull(repo.branches());

			assertFalse(repo.name().isBlank());
			assertFalse(repo.ownerLogin().isBlank());
			assertFalse(repo.branches().isEmpty());

			assertEquals(repo.ownerLogin(), TEST_USERNAME);

			for (GithubRepositoryBranchDTO branch : repo.branches()) {
				assertNotNull(branch.name());
				assertNotNull(branch.sha());

				assertFalse(branch.name().isBlank());
				assertFalse(branch.sha().isBlank());
			}
		}
	}
}
