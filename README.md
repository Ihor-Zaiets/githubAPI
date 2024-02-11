## My thought process

10.02.2024

I know it's important for employers to know candidate. So I decided to add this part of readme for recruitment process
purposes, to give employers an opportunity to see, not only what code I was able to write,
but also my thought process behind this.

As I understood assigment, there is GitHub api. My application should use this api to retrieve information from GitHub
with given parameters. I started from using spring.io to set up a project. 
Added spring web and spring jpa, although there was no word about
saving any data into database in assigment, I added spring jpa just because how used to this I am. 
Spring web for web development.
Spring JPA for saving and reading data to\form database.

This assignment was for juniors. If I pretend to have a higher skills than juniors, I guess I should do something more
than just written assigment. It was said, that to keep honesty of recruitment process, questions about assigment won't be
answered. Therefore, I should decide what to do myself. There was no requirement of saving data to database, but using
external API. Then, I would consider this a main focus. Therefore, from two ideas I currently have: add saving to database
and add authorization to retrieve more data from GitHub, I choose the second. I also want to do second option more,
because I already quite good at creating entities, dtos, a dozen mappers, 
repositories and services to set up CRUD business logic.
But I barely never used external API in my application and also had problems with Spring Security and tokens 
last time I used it.

#### "If you only do what you can do, you'll never be more than you are now".

After deciding what my plan is and setting up my project, I'm creating folder structure, controllers and dtos. 

Now, when I finished creating structure of the project, I can use TDD methodology. I'm going to create tests before I create
business logic. I will separate tests into two groups: unit and integration. For test writing I use JUnit5 and Mockito.

Also, a couple of notes: 

1. intellij idea suggest to not use @Autowired on fields, and it's better to use it on setters and 
constructors. I tried both constructors and fields. Using it on fields is very simple and straightforward, but constructor
is just a pain. I didn't notice any problems with field injection and constructor injections was working just as fine.
But managing constructor injections is a drastically more work.
2. I know it's better to create interface, declare method there and then create its implementations. In such way application
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

