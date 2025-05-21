# Backend - BreakableToy2

This is the backend component of the BreakableToy2 project, developed using Spring Boot and Java 21 (Gradle). It provides RESTful APIs that interact with the Spotify Web API and serve data to the frontend application.

## Features

- Java 21 with Spring Boot and Gradle
- RESTful API structure
- Spotify Web API integration
- OAuth 2.0 Authorization Code Flow
- Automatic token refresh and retry on 401 errors
- Rate-limiting handling (HTTP 429 with Retry-After)
- Docker support for containerized deployment
- Code coverage with JaCoCo

## Getting Started

1. Navigate to the backend directory:
   ```bash 
   cd backend
   ```

2. Run the application using Gradle:
   ```bash
   ./gradlew bootRun
   ```

3. Access the backend at:
   ```bash
   http://localhost:8080
   ```

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

## Environment Variables

This application uses environment variables to securely manage sensitive configuration like Spotify credentials.

Create a `.env` file in the root directory and add the following:

```env
SPOTIFY_CLIENT_ID=your-client-id
SPOTIFY_CLIENT_SECRET=your-client-secret
SPOTIFY_REDIRECT_URI=http://127.0.0.1:8080/auth/callback
```

The `.env` file is automatically read by Docker Compose. Make sure to **not commit this file** — it's already listed in `.gitignore`.

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

Includes full test coverage for:

- OAuth login and token flow
- Artist and album controller tests
- Token storage and refresh logic
- Retry on expired tokens (401)
- Basic search functionality

Technologies: **JUnit 5**, **Mockito**, **MockMvc**, **Spring Boot Test**

## Spotify OAuth 2.0 Authentication Flow

This backend application integrates with the Spotify Web API using OAuth 2.0 Authorization Code Flow.

1. **User clicks login**: Redirects to Spotify's login page.
2. **Spotify callback**: Redirects back with a `code`.
3. **Token exchange**: Backend exchanges `code` for `access_token` and `refresh_token`.
4. **Storage**: Tokens are stored in memory.
5. **Refresh logic**: Expired access tokens are refreshed automatically.
6. **401 retry**: If an API call returns 401, token is refreshed and request is retried.
7. **429 retry**: If Spotify responds with 429, backend waits `Retry-After` and retries.

## Rate Limiting Handling (HTTP 429)

If Spotify returns a 429 (Too Many Requests), the backend:

- Detects the status code.
- Extracts `Retry-After` header.
- Waits and retries up to 3 times.
- Logs the retry event for debugging.

This prevents breaking the app during heavy usage and follows Spotify’s best practices.

## Key Files

- `OAuthService.java`: OAuth flow, refresh logic.
- `SpotifyTokenStore.java`: Stores token and expiration info.
- `SpotifyAuthInterceptor.java`: Automatically refreshes token and retries on 401.
- `ArtistService.java`: Handles all Spotify API calls and 429 retry logic.

## License

This project is licensed under the MIT License.