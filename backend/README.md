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

### Token Refresh Logic
To ensure continued access to Spotify's Web API without requiring the user to log in again, this backend implements a **token refresh mechanism**

### How it works

1. **Access Token Expiry Detection**:
   - The application checks if the access token has expired before making requests.
   - This is done using the `SpotifyTokenStore` class, which tracks the token's expiration time (`expiresAt`).

2. **Automatic Refresh on Expiry**:
   - If the token is expired, the method `refreshAccessToken()` in `OAuthService` uses the stored refresh token to request a  new access token.
   - The new token and its expiration time are stored again in memory.

3. **Transparent Retry on 401**
   - If a request to Spotify's API returns `401 Unauthorized`, the `SpotifyAuthInterceptor` catches it.
   - It triggers a token refresh and retries the failed request automatically.

4. **Token Caching**:
   - When a token is refreshed, its new expiration timestamp is stored using `Instant.now().plusSeconds(expiresIn)`.

### Key Files

- `OAuthService.java`: Contains the logic to detect expiration and refresh tokens.
- `SpotifyTokenStore.java`: Stores access/refresh tokens and calculates expiration time.
- `SpotifyAuthInterceptor.java`: Handles retrying failed Spotify request transparently.

### No user re-login needed

This logic ensures the user stays logged in, as long as the refresh token is valid.

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