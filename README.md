# Spring Security JWT Authentication Demo

A Spring Boot application demonstrating JWT (JSON Web Token) based authentication and authorization using Spring Security.

## Overview

This project implements a secure REST API with JWT authentication. It uses Spring Security for authentication and authorization, JPA for data persistence, and provides endpoints for user authentication and protected resources.

## Technologies Used

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- JSON Web Tokens (JWT)
- Jakarta EE

## Features

- JWT based authentication
- Stateless session management
- Protected API endpoints
- Database-backed user authentication
- Role-based access control

## API Endpoints

### Public Endpoints
- `POST /authenticate` - Authenticate user and get JWT token

### Protected Endpoints
- `GET /` - Home endpoint (requires authentication)
- `GET /user` - User specific endpoint (requires USER role)
- `GET /admin` - Admin specific endpoint (requires ADMIN role)

## Authentication Flow

1. Client sends credentials to `/authenticate`
2. Server validates credentials and returns JWT token
3. Client includes JWT token in Authorization header for subsequent requests
4. Server validates token for each protected endpoint request

## Project Structure
```
spring_security_phase1/ ├── src/main/java/com/uk/spring_security_phase1/ │ ├── config/ │ │ └── SecurityConfiguration.java │ ├── controllers/ │ │ └── HomeController.java │ ├── models/ │ │ ├── AuthenticationRequest.java │ │ ├── AuthenticationResponse.java │ │ ├── MyUserDetails.java │ │ └── UserEntity.java │ ├── repositories/ │ │ └── UserRepository.java │ ├── services/ │ │ └── MyUserDetailsService.java │ ├── util/ │ │ └── JWTUtil.java │ └── SpringSecurityPhase1Application.java
``` 

## Setup and Configuration

### Prerequisites
- JDK 21
- Maven
- Your favorite IDE (IntelliJ IDEA recommended)

### Running the Application

1. Clone the repository:
```
bash git clone [repository-url]
``` 

2. Navigate to project directory:
```
bash cd spring_security_phase1
``` 

3. Build the project:
```
bash mvn clean install
``` 

4. Run the application:
```
bash mvn spring-boot:run
``` 

### Configuration Properties

Configure the following properties in `application.properties`:
```
properties
# Database Configuration
spring.datasource.url=your-database-url spring.datasource.username=your-username spring.datasource.password=your-password
# JWT Configuration
jwt.secret=your-jwt-secret jwt.expiration=36000
``` 

## Security Considerations

- The default configuration uses BCrypt password encoding
- CSRF protection is disabled for API endpoints
- Session management is configured to be stateless
- JWT tokens should be transmitted over HTTPS in production

## Testing the API

### Authentication Request
```
bash curl -X POST [http://localhost:8080/authenticate](http://localhost:8080/authenticate)
-H "Content-Type: application/json"
-d '{"username": "user", "password": "password"}'
``` 

### Accessing Protected Endpoint
```
bash curl -X GET [http://localhost:8080/user](http://localhost:8080/user)
-H "Authorization: Bearer <your-jwt-token>"

