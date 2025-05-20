import { render, screen, waitFor } from '@testing-library/react';
import Dashboard from '../pages/Dashboard';
import { AuthContext } from '../context/AuthContext';
import { MemoryRouter } from 'react-router-dom';

global.fetch = vi.fn(() =>
  Promise.resolve({
    ok: true,
    json: () => Promise.resolve([{ name: 'Artist 1', id: '1', imageUrl: 'image.jpg' }]),
  })
) as any;

describe('Dashboard', () => {
  it('displays artists after fetch', async () => {
    render(
      <AuthContext.Provider value={{ token: 'fake-token', setToken: () => {} }}>
        <MemoryRouter>
          <Dashboard />
        </MemoryRouter>
      </AuthContext.Provider>
    );

    expect(screen.getByText(/Loading/i)).toBeInTheDocument();

    await waitFor(() => {
      expect(screen.getByText(/Artist 1/i)).toBeInTheDocument();
    });
  });
});