# Frontend - BreakableToy2

This is the frontend for the BreakableToy2 project, built with **React**, **TypeScript**, and **Vite**.

## Getting Started

### Install Dependencies

```bash
npm install
```

### Run Development Server

```bash
npm run dev
```

The app will be available at 
```
[http://localhost:5173](http://localhost:5173)
```

---

## Project Structure

```
frontend/
├── src/
│   ├── components/
│   ├── context/
│   ├── hooks/
│   ├── pages/
│   ├── services/
│   ├── types/
│   ├── App.tsx
│   └── main.tsx
```

## Authentication Flow

The app uses **Spotify OAuth Authorization Code Flow** (with PKCE handled by the backend) to authenticate users.

### How it works:

1. User clicks Login with Spotify


2. Frontend sends request to POST `/auth/spotify` (backend)


3. Backend returns Spotify authorization URL → user is redirected


4. After user logs in and approves, Spotify redirects to `?access_token=...`


5. Frontend reads the token, saves it to React Context and localStorage


6. App routes user to `/dashboard` where token is used in authorized requests



---

## Auth Context Implementation

The frontend uses `AuthContext` for centralized token management:

Access token is stored in memory and `localStorage`

Auto-persisted across sessions

Logout button clears context and redirects to `/login`

Pages like `/dashboard`, `/artist/:id` and `/album/:id` validate auth state before rendering



---

## Features

### Dashboard

- Route: `/dashboard`

- Displays user’s Top 10 Spotify Artists

- Includes search for artists, albums, or tracks

- Clickable cards navigate to detail views


### Artist Page

- Route: `/artist/:id`

- Shows artist’s name, genres, image, and bio placeholder

- Displays list of their albums in rows of 5


### Album Page

- Route: `/album/:id`

- Shows album details (name, cover, release date, total tracks)

- Lists track names styled similarly to Spotify


### Logout

- Appears on `/dashboard`

- Clears stored token and redirects to `/login`



---

## Error Handling

- If user visits protected routes (`/dashboard`, `/artist`, `/album`) without a token, they are redirected to `/login`

- Handles invalid or expired tokens

- Displays loading and error states during fetch



---

## Testing

Uses **Vitest** + **React Testing Library** to test components and pages.

### Run all tests:

`npx vitest run`

### Example tests:

- `Dashboard.test.tsx`: tests artist rendering

- `ArtistPage.test.tsx`: tests API mocks and DOM rendering

- `Login.test.tsx`: tests button behavior and redirect call



---

## Dev Tools

- **Vite**: Fast build and HMR

- **React Router**: Declarative routing

- **React Context API**: Auth state management

- **Vitest + RTL** : Unit testing

- **Prettier + ESLint**: Code quality



---

## Notes

- For simplicity, tokens are stored client-side (localStorage + context)

- This should be secured or moved to server-only session in production

- You must configure the Spotify redirect URI to include:

```
http://localhost:5173/
```



---

## License

MIT License

---