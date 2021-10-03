# Kry Codetest

Code test for Kry by Jonas Lundholm

## Dependencies

- Java 11
- Gradle 6.9
- Docker (20.0.0 >=) || Docker Compose
- Yarn

# Components

### server

The backend poller which fetches the state of the services. Supports full CRUD functionality.

### poller-client

React frontend used for adding and removing services

### db

Initial SQL that will be run on upstart of MySQL

### slowserver

A local node.js server used to simulate behavior with slow connections. Accepts
requests on localhost:3000 within the docker network and takes 10 seconds to respond.

# Building the application

Create a .env file in the root folder, it should have the following properties

```
MYSQL_ROOT_PASSWORD=root-password
MYSQL_DATABASE=kry
MYSQL_USER=user
MYSQL_PASSWORD=<password>
```

In server/src/main/resources/application.yml, copy and paste

application.yml

```
spring:
  datasource:
    url: jdbc:mysql://mysql:3306/kry
    username: <username from .env>
    password: <password from .env>
    driver-class-name: com.mysql.jdbc.Driver
```

In root folder, copy and paste

```
cd server && ./gradlew build

```

The MySQL Docker image is weird, so for the account to "activate" and the server to
be able to start using it you need to log in to the account once.

```
docker exec -i mysql mysql -u<username from .env> -p<password from .env>
```

In poller-client root folder, copy and paste .env file with value of server host.

.env

```
REACT_APP_API_HOST=http://localhost:8081


# Running the applications

```

In the root folder, run docker-compose up -d (docker compose up will also work in more recent versions)

```


In root folder, run
docker-compose up -d

cd poller-client && yarn && yarn start

```

poller-client is accessible on port 3000
server is accessible on port 8080 through Docker

# What I would have done with more time

```
Add Makefile for building the different projects
```
