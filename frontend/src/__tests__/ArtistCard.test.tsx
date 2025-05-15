import { render, screen } from '@testing-library/react';
import ArtistCard from '../components/ArtistCard';

describe('ArtistCard', () => {
  it('displays artist name and image', () => {
    render(<ArtistCard name="Soda Stereo" imageUrl="http://example.com/image.jpg" />);

    expect(screen.getByText('Soda Stereo')).toBeInTheDocument();
    const img = screen.getByRole('img');
    expect(img).toHaveAttribute('src', 'http://example.com/image.jpg');
  });
});
