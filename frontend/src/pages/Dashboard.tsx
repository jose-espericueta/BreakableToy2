import { useEffect, useState, useContext } from 'react';
import ArtistCard from '../components/ArtistCard';
import { AuthContext } from '../context/AuthContext';

const Dashboard = () => {
  const { token } = useContext(AuthContext);
  const [artists, setArtists] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {

    console.log('Token being sent: ', token);

    if (!token) return;

    fetch('http://127.0.0.1:8080/auth/me/top/artists', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then(res => {
        if (!res.ok) throw new Error('Failed to fetch');
        return res.json();
      })
      .then(data => setArtists(data))
      .catch(err => setError(err.message))
      .finally(() => setLoading(false));
  }, [token]);

  if (loading) return <p>Loading top artists...</p>;
  if (error) return <p>Error: {error}</p>;
  if (artists.length === 0) return <p>No artists found</p>;

  return (
    <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(160px, 1fr))', gap: 20 }}>
      {artists.map((artist, index) => (
        <ArtistCard key={index} name={artist.name} imageUrl={artist.imageUrl} />
      ))}
    </div>
  );
};

export default Dashboard;
