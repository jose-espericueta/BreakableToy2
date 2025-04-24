# Backend - BreakableToy2

This is the backend component of the BreakableToy2 project, developed using Spring Boot and Java 21 (Gradle). It provides RESTful APIs that interact with the Spotify Web API and serve data to the frontend application.

## Features

- Java 21 with Spring Boot and Gradle
- RESTful API structure
- Spotify Web API integration
- OAuth 2.0 support 
- Docker support for containerized deployment
- Code coverage with JaCoCo

## Getting Started

1. Navigate to the backend directory:
   ```bash
   cd backend

2. Run the application using Gradle:
   ```bash
   ./gradlew bootRun

3. Access the backend at:
   ```bash
   http://localhost:8080
   
## Running with Docker

To build and run the backend using Docker:

1. Build the image:
   ```bash
   docker build -t spotify-backend .
   
2. Run the container:
   ```bash
   docker run -p 8080:8080 spotify-backend
   
## Requirements

- Java 21
- Gradle
- Docker

## Testing 

Run unit tests and generate code coverage report with:

```bash
./gradle test
```
JaCoCo reports will be available under

```bash
build/reports/tests/test/index.html
```
   
## License

This project is licensed under the MIT License.