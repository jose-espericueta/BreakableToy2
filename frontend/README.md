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