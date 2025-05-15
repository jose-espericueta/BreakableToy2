# BreakableToy2

BreakableToy2 is a web application built using Spring Boot for the backend and React for the frontend. It serves as a full-stack project to demonstrate the integration of a Java-based REST API with a modern JavaScript frontend framework.

## Features
- Backend: Spring Boot for RESTful APIs and business logic.
- Frontend: React for a dynamic and responsive user interface.
- Integration: Seamless communication between the backend and frontend using JSON.

## Getting Started
1. Clone the repository.
2. Run the Spring Boot application (`./mvnw spring-boot:run`).
3. Start the React development server (`npm start`).
4. Access the application in your browser at `http://localhost:3000`.

## Requirements
- Java 17+
- Node.js and npm
- Maven

## License
This project is licensed under the MIT License.

## Top Artists Feature

After logging in with Soptify, users are redirected to a dashboard where their top 10 artists are displayed. Each artist is shown with their name and image in a responsive card layout.

### Error Handling and States
The dashboard includes visual feedback for loading states, error messages if the data cannot be fetched, and appropriate UI handling for empty artist lists.

### Frontend Tests
Tests are written using Vitest and React Testing Library to ensure reliability:
- `Dashboard.test.tsx`: Test data fetching and display logic.
- `Login.test.tsx`: Tests Spotify login button and redirection.
- `ArtistCard.test.tsx`: Tests correct rendering of individual artist components.

To run the tests:

```bash
npm install
npm run test
```