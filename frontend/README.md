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

The app will be available at [http://localhost:5173](http://localhost:5173)

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

This modular structure helps keep the code scalable and maintainable.

---

## Linting & Formatting

### Lint

```bash
npm run lint
```

### Lint and fix

```bash
npm run lint:fix
```

### Format with Prettier

```bash
npm run format
```

---

## Testing

This project uses **Vitest** and **React Testing Library** for unit testing.

### Run tests

```bash
npx vitest run
```

---

## Tech Stack

- React + TypeScript
- Vite
- ESLint + Prettier (Airbnb config)
- React Testing Library + Vitest

## Login Flow (Frontend)

The application includes a login page to initiate the Spotify OAuth flow.

### How it works:
1. When the user visits `/login`, they see a "Login with Spotify" button.
2. Clicking the button redirects the user to the backend endpoint `/auth/spotify` to start the OAuth process.
3. Upon successful authentication, Spotify redirects the user back with an `access_token` as a query parameter.
4. The frontend parses the token and stores it in `localStorage` and React Context.
5. The user is then redirected to the `/dashboard` page.

### Technologies used:
- React Router for page navigation
- React Context API for token storage
- LocalStorage to persist token
- Vitest + Testing Library for testing

### Error Handling:
If the token is not present or invalid, the app remains on the login page.

---

## Top Artists Feature

Once the user logs in, the app fetches their top 10 artists from Spotify and displays them on the dashboard.

- Each artist card shows the name and image
- Uses Spotify's `GET /v1/me/top/artists` endpoint via backend
- Handles loading, error and empty states

---

## Artist and Album Detail Pages

As part of feature **#9**, the app now supports detailed views for both artists and albums.

### Artist Detail Page
- Route: `/artist/:id`
- Shows artist name, image, genres and biography
- Lists all albums by the artist (clickable)

### Album Detail Page
- Route: `/album/:id`
- Shows album name, cover image, release date and number of tracks
- Lists all tracks in the album

### Backend Support
- `GET /artists/{id}` returns artist details
- `GET /artists/{id}/albums` returns list of albums
- `GET /albums/{id}` returns album info and tracks

### Tests
- Added backend tests for these endpoints
- Frontend tests cover page rendering and navigation (where applicable)

