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

To run unit tests and generate a code coverage report:

```bash
./gradlew test
```

JaCoCo coverage reports are available at:

```
build/reports/tests/test/index.html
```

### Covered Tests

The following OAuth-related components are covered with unit tests:

- **OAuthService**
   - `buildAuthUri()`: Ensures the correct Spotify login URL is generated
- **AuthController**
   - `POST /auth/spotify`: Returns the redirect URL to initiate Spotify login
   - `GET /auth/callback`: Simulates receiving the code and exchanging it for tokens

All tests use **JUnit 5**, **Mockito**, and **MockMvc** for controller testing.

## Spotify OAuth 2.0 Authentication Flow

This backend application integrates with the Spotify Web API using OAuth 2.0 Authorization Code Flow. Below is a summary of how the authentication process works:

### Authentication Steps

1. **Initiate Login**
   - `POST /auth/spotify`
   - Returns a `redirectUrl` that points to Spotify’s login/authorization page.

2. **Redirect to Spotify**
   - The frontend (or user) uses the `redirectUrl` to navigate to Spotify’s consent page.

3. **User Grants Access**
   - After login and authorization, Spotify redirects the user to the backend callback URL:
     ```
     http://127.0.0.1:8080/auth/callback?code=...
     ```

4. **Backend Exchanges Code for Tokens**
   - The `code` from Spotify is received and exchanged for an `access_token` and `refresh_token` via:
     ```http
     GET /auth/callback?code=...
     ```

5. **Token Storage**
   - Tokens are securely stored in memory using a singleton component (`SpotifyTokenStore`) so they can be reused for authenticated requests to Spotify.

### Data Flow

- **Request**: Backend → Spotify (`/api/token`)
- **Response**: Spotify returns token data, which is mapped via `SpotifyTokenResponse` DTO.
- **Storage**: Tokens are saved temporarily in memory (not persisted across server restarts).

### Security Notes

- Tokens are never logged or exposed to the client directly.
- Storage is temporary (RAM-only) for development purposes.
- Can be replaced with secure DB storage in production.
   
## License

This project is licensed under the MIT License.