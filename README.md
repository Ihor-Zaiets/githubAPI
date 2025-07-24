# Project description
This app is used to send requests to GitHub API to retrieve data about user's repositories and theirs branches.

## Setup
Application does not save any data and does not have any datasource. It is enough to download code, then maven dependencies
and application is ready to go. By default, Tomcat server is running on localhost and listening on port 8080. You can
change that by uncommenting port property <code>#server.port=8081</code> in <code>application.properties</code> file.

## Usage
Application's API is presented as one endpoint: <code>GET</code><code>/api/github/getUserRepositoriesInfo/{username}</code>. This endpoint
consumes username as path variable. For given username 
application will return all repositories, which are not forks. For each repository its name, owner login and list of branches.
For each branch its name and last commit sha.

Beware, application doesn't implement any authorization to GitHub, therefore GitHub will allow only 50 requests per hour
for one IP. Test in application doesn't mock GitHub response, so running test will also consume available requests to 
GitHub API.

## Exceptions
If user with given name does not exist, application will return status code <code>404</code> <code>NOT_FOUND</code>.
