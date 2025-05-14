import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import { vi } from 'vitest';
import Login from '../pages/Login';

describe('Login Page', () => {
  it('renders login button', () => {
    render(<Login />);
    expect(screen.getByText(/Login with Spotify/i)).toBeInTheDocument();
  });

  it('redirects on button click', async () => {
    const originalLocation = window.location;

    delete window.location;
    window.location = {
      assign: vi.fn(),
    } as unknown as Location;

    render(<Login />);
    const button = screen.getByText(/Login with Spotify/i);
    await userEvent.click(button);

    expect(window.location.assign).toHaveBeenCalledWith('http://127.0.0.1:8080/auth/spotify');

    window.location = originalLocation;
  });
});