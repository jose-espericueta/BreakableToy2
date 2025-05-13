import { render, screen } from '@testing-library/react';
import App from '../App';

describe('App', () => {
  it('renders the home page text', () => {
    render(<App />);
    expect(screen.getByText(/Home \(Login\)/i)).toBeInTheDocument();
  });
});
