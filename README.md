# jukebox control settings Application

Jukebox is a springBoot based Microservice to return jukeboxes based in the Settings configId.
Additionally it is possible the filter by jukeBox model.
The Microservice is configured to be deployed in a docker container. 

## Installation
As is a docker service is necessary that your machine has a docker service running in your operative System.
there are 2 options to run the container and have the app ready to use:

- 1st Option: Open a terminal and go to the project root folder type

`docker image build -t jukebox-control-settings . `

The -t option allows you to specify a name and optionally a tag. 
If you don't specify a tag Docker will automatically tag the image as 'latest'.

Now that the image is ready and built you will be able to run a container with the following command

`docker container run -p 8080:8080 jukebox-control-settings`

The run command takes an optional -p parameter that allows you to map a port from the container to your host machine

then if you want to check the instance and ports where your app is running run the following command 
`docker ps`

-2nd Option: Use the docker compose file so All you need to do is open a terminal and go to the project directory and run the  
following command to build it:

`docker-compose build`

then to create and start containers.

`docker-compose up`
or

`docker-compose -f <compose file name> up`

Note: you can avoid -fif your file name is docker-compose.yml . If you want to run your services in the background, you can pass the -d flag (for “detached” mode)

Below the commands will stop containers.

`docker-compose stop`

You can bring everything down and remove all containers entirely with command:
`docker-compose down`

##Executing the services

To test the service the actuator health check could be use using the following URL:

http://localhost:8080/actuator/health

The service is also configured with swagger to Document and provide the API ui to interact
with the different Endpoints in the following URL:

http://localhost:8080/swagger-ui.html

Note: The url are with "LOCALHOST" please use the name of the server or IP according of your machine settings 

## Project status

Currently the project is under development and has the basic configuration for a "ready to use"
As a pending activities are to configure:
- Spring Security with OAuth token authentication. 
- Configure of the jenkins build
- Configuration for checkstyle
- Configuration for integration with Sonar 
- Configuration for the service Discovery 
- Configuration for the Resource Discovery and HATEOAS
