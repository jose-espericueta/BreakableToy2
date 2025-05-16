import { render, screen, waitFor } from "@testing-library/react";
import AlbumPage from "../pages/AlbumPage";
import { AuthContext } from "../context/AuthContext";
import { MemoryRouter, Route, Routes } from "react-router-dom";

global.fetch = vi.fn(() =>
  Promise.resolve({
    ok: true,
    json: () =>
      Promise.resolve({
        name: "Test Album",
        imageUrl: "http://album.url",
        totalTracks: 2,
        releaseDate: "2025-01-01",
        trackNames: ["Track 1", "Track 2"],
      }),
  })
) as any;

describe("AlbumPage", () => {
  it("renders album details", async () => {
    render(
      <AuthContext.Provider value={{ token: "fake-token", setToken: () => {} }}>
        <MemoryRouter initialEntries={["/album/abc"]}>
          <Routes>
            <Route path="/album/:id" element={<AlbumPage />} />
          </Routes>
        </MemoryRouter>
      </AuthContext.Provider>
    );

    expect(screen.getByText(/Loading/i)).toBeInTheDocument();

    await waitFor(() => {
      expect(screen.getByText("Test Album")).toBeInTheDocument();
      expect(screen.getByText("Track 1")).toBeInTheDocument();
    });
  });
});
