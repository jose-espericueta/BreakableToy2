# BreakableToy2 

BreakableToy2 is a full-stack music discovery application powered by the **Spotify Web API**, built using **Spring Boot (Java)** for the backend and **React (TypeScript)** for the frontend. It allows users to log in with Spotify, explore their top artists, albums, and tracks, and interact with their music library in real time.

---

##  Tech Stack

| Layer      | Technology                        |
|------------|------------------------------------|
| Frontend   | React, TypeScript, Vite            |
| Backend    | Java 21, Spring Boot, Gradle       |
| Auth       | OAuth 2.0 with Spotify             |
| Styling    | Pure CSS                           |
| Testing    | Vitest, React Testing Library, JUnit, Mockito |
| Containerization | Docker, Docker Compose      |

---

##  Features

- **Spotify Login**: Secure authentication using Spotify OAuth 2.0.
- **Dashboard**: Displays top 10 artists based on user listening habits.
- **Search**: Find any artist, album, or track from Spotify’s global database.
- **Artist Page**: View artist genres, image, and related albums.
- **Album Page**: View album details and full tracklist.
- **Auto Token Refresh**: Access tokens are automatically refreshed when expired.
- **Rate Limit Handling**: Graceful retry if Spotify API returns `429 Too Many Requests`.

---

##  Project Structure

```plaintext
BreakableToy2/
│
├── backend/          # Java Spring Boot API
│   ├── controller/
│   ├── service/
│   ├── auth/         # OAuth + token management
│   └── ...
│
├── frontend/         # React + Vite app
│   ├── pages/
│   ├── components/
│   ├── context/
│   └── ...
│
├── docker-compose.yml
├── .env
└── README.md
```

## Getting Started
### Prerequisites
- Java 21

- Node.js + npm

- Docker & Docker Compose (optional)


**1. Clone the Repo**
```
git clone https://github.com/your-org/BreakableToy2.git
cd BreakableToy2
```

**2. Run with Docker Compose**

```
docker-compose up --build
```
App will be available at:

Frontend → `http://localhost:5173`

Backend → `http://localhost:8080`

**3. Environment Variables**

Create a `.env` file in the root directory:
```
SPOTIFY_CLIENT_ID=your-client-id
SPOTIFY_CLIENT_SECRET=your-client-secret
SPOTIFY_REDIRECT_URI=http://127.0.0.1:8080/auth/callback
```
This config is used by the backend to authenticate with Spotify.

## Running Tests
Backend (JUnit + Mockito)
```
cd backend
./gradlew test
```
Test reports: `backend/build/reports/tests/test/index.html`

### Frontend (Vitest + Testing Library)
```
cd frontend
npm install
npx vitest run
```
## Authentication Flow (Spotify OAuth)
1. User clicks **Login with Spotify**.

2. User is redirected to Spotify’s login page.

3. After login, Spotify redirects back to your backend with a code.

4. Backend exchanges the code for access_token + refresh_token.

5. Tokens are stored in memory and automatically refreshed when expired.

## Implementation Details
- Token Refresh: SpotifyAuthInterceptor retries failed requests on 401 Unauthorized.

- Rate Limiting: All Spotify requests check for 429 responses and back off using Retry-After header.

- Logout: Frontend includes a Logout button that clears local tokens and redirects to login.

## License
This project is licensed under the MIT License.