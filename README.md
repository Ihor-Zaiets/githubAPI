# Project description
This app is used to send requests to GitHub API to retrieve data about user's repositories and its branches.

## Setup
Application does not save any data and does not have any datasource. It is enough to download code and maven dependencies
and application is ready to go. By default, Tomcat server is running on localhost and listening on port 8080. You can
change that by uncommenting port property <code>#server.port=8081</code> in <code>application.properties</code> file.

## Usage
Application's API is presented as one endpoint: <code>POST</code><code>/api/github/getUserRepInfo</code>. This endpoint
consumes application/json content type and request body should contain GitHub user username. For given username 
application will return all repositories, which are not forks. For each repository its names, owner username and list of branches.

## Exceptions
If user with given name does not exist, application will return status code 404 <code>NOT_FOUND</code>. For any other 
exception application will return 500 <code>INTERNAL_SERVER_ERROR</code> and asks to contact administrator.

## My thought process

10.02.2024

I know it's important for employers to know candidate. So I decided to add this part of readme for recruitment process
purposes, to give employers an opportunity to see, not only what code I was able to write,
but also my thought process behind this.

As I understood assigment, there is GitHub api. My application should use this api to retrieve information from GitHub
with given parameters. I started from using spring.io to set up a project.

#### "If you only do what you can do, you'll never be more than you are now".

After deciding what my plan is and setting up my project, I'm creating folder structure, controllers and dtos. 

Now, when I finished creating structure of the project, I can use TDD methodology. I'm going to create tests before I create
business logic. I will separate tests into two groups: unit and integration. For test writing I use JUnit5 and Mockito.

Also, a couple of notes: 

1. intellij idea suggest to not use @Autowired on fields, and it's better to use it on setters and 
constructors. I tried both constructors and fields. Using it on fields is very simple and straightforward, but constructor
is just a pain. I didn't notice any problems with field injection and constructor injections was working just as fine.
But managing constructor injections is a drastically more work.
2. I know it's better to create interface, declare methods there and then create its implementations. In such way application
is much more flexible, and you can later just change implementation in case of a problem. On the other hand, there is no need
to create an interface and then only 1 implementation according to KISS(~~keep it simple, stupid~~ keep it super simple), that's
why I wrote implementations without interfaces straight ahead.

11.02.2024

Today i was solving two main problems:
1. Usually, I was always creating applications, that are receiving requests, but not sending it. So new topic for me, I googled it.
First thing I found is HttpURLConnection, which is really weird, because later I found out, it's 14+ years old technology,
but tutorial was written a couple of month ago. Generally there was a lot of tutorials for HttpURLConnection, which is, if
I understand correctly, firstly, an old technology, and secondly, a low-level technology. Later I found out HttpClient
as continuation and more high level solution for sending requests. But there were problems too. I found two different sites,
that were telling diametrically opposite. HttpClient is old version and HttpURLConnection is new and vice versa and what should I use.
Then, i googled more, found other frameworks for that and decided to stop on Spring WebClient as the most similar technology
to what i`m used to. And successfully send a GET request and get all repos for my login. For info about branches i need to do
the same but on different url. Turned out, /repos/{owner}/{repo}/branches already sending last commit sha and name for every
branch, so that's a piece of cake.
2. As response from GitHubAPI I'm getting JSON. It easily converted to String or to Object, but I need to access described fields.
So my goal is to deserialize JSON into my DTO object. I had this in my project at work, but that was set up once by someone else and was working
correctly, so i never had any experience with it. So, i have doubts, i wrote this in the best way possible, but i managed to
do this too. I used Postman to send requests to GitHub API to see, what kind of data does it return.

Okay, i finished a main part of assigment, sending requests and retrieving required data is working correctly. It's made
in test class, but transfer it to service is 10 seconds thing. What i really need to do, is add validations, exceptions
and i'd really want to write it using TDD, but i just don't know, how to write this test. I noticed, that, maybe, it's 
a good practise, to write code using TDD, but it's really hard to write test for business logic if i have no idea, how
it should work.

My attempts to use TDD is greatly slowing me down, so i decided to wrote code and then create tests
for it.

13.02.2024

Today I finally finished exception handling. Creating exceptions and handle them globally is something i have done before.
So that was easy. The main problem for me was handling exception send by GitHub API, because i somehow should've used
available methods in Spring WebClient to throw my custom exception. After 2 days of googling i finally made it.

14.02.2024

Today I decided to write tests. I understood, why i couldn't figure out, how to write tests using TDD. In the past, 
i was using Mock and JUnit5 to mock class dependencies and methods behavior, but in this case i was asking myself, how to mock
external API response? And that was a very good question which i googled later and found out. I needed to use MockWebServer
to mock external API. While i was writing this test, i noticed, how i being stuck at the smallest of things. I guess,
that is one of those moment, when you should just let it be. i couldn't make it work. For some reason, during test,
request sends and loading is never ends. Maybe it's not good to left broken code in assigment, but i consider this be better,
than nothing.
