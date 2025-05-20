import { render, screen, waitFor } from "@testing-library/react";
import ArtistPage from "../pages/ArtistPage";
import { AuthContext } from "../context/AuthContext";
import { MemoryRouter, Route, Routes } from "react-router-dom";

global.fetch = vi.fn((url) => {
  if (url.includes("artists/123/albums")) {
    return Promise.resolve({
      ok: true,
      json: () =>
        Promise.resolve([
          {
            id: "album1",
            name: "Test Album",
            imageUrl: "http://album.url",
          },
        ]),
    });
  }
  if (url.includes("artists/123")) {
    return Promise.resolve({
      ok: true,
      json: () =>
        Promise.resolve({
          name: "Test Artist",
          imageUrl: "http://image.url",
          biography: "Biography not available",
          genres: ["rock", "pop"],
        }),
    });
  }
  return Promise.reject("Unknown API");
}) as any;

describe("ArtistPage", () => {
  it("renders artist and albums", async () => {
    render(
      <AuthContext.Provider value={{ token: "fake-token", setToken: () => {} }}>
        <MemoryRouter initialEntries={["/artist/123"]}>
          <Routes>
            <Route path="/artist/:id" element={<ArtistPage />} />
          </Routes>
        </MemoryRouter>
      </AuthContext.Provider>
    );

    expect(screen.getByText(/Loading/i)).toBeInTheDocument();

    await waitFor(() => {
      expect(screen.getByText("Test Artist")).toBeInTheDocument();
      expect(screen.getByText("Test Album")).toBeInTheDocument();
    });
  });
});