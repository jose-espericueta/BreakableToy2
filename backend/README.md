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

```bash
docker build -t spotify-backend .
docker run -p 8080:8080 spotify-backend
```

## Running with Docker Compose

To build and run the backend using Docker Compose:

```bash
docker-compose up --build
```

The backend will be available at:

```
http://localhost:8080
```

## Requirements

- Java 21
- Gradle
- Docker (optional for containerized deployment)
- Docker Compose (for multi-container orchestration)

## Testing 

Run unit tests and generate code coverage report with:

```bash
./gradle test
```
JaCoCo reports will be available under

```
build/reports/tests/test/index.html
```
   
## License

This project is licensed under the MIT License.