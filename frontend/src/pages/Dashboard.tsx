import { useEffect, useState, useContext } from 'react';
import ArtistCard from '../components/ArtistCard';
import { AuthContext } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';

const Dashboard = () => {
  const { token } = useContext(AuthContext);
  const [artists, setArtists] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [searchQuery, setSearchQuery] = useState('');
  const [debouncedQuery, setDebouncedQuery] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    const handler = setTimeout(() => {
      setDebouncedQuery(searchQuery);
    }, 1000);

    return () => {
      clearTimeout(handler);
    };
  }, [searchQuery]);

  useEffect(() => {
    if (!token) return;

    const endpoint = debouncedQuery
      ? `http://127.0.0.1:8080/artists/search?q=${debouncedQuery}&type=artist`
      : `http://127.0.0.1:8080/artists/me/top`;

    setLoading(true);

    fetch(endpoint, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then(res => {
        if (!res.ok) throw new Error('Failed to fetch');
        return res.json();
      })
      .then(data => {
        const result = debouncedQuery ? data.artists?.items ?? [] : data;
        setArtists(result);
      })
      .catch(err => setError(err.message))
      .finally(() => setLoading(false));
  }, [token, debouncedQuery]);

  if (loading) return <p>Loading artists...</p>;
  if (error) return <p>Error: {error}</p>;
  if (artists.length === 0) return <p>No artists found</p>;

  return (
    <div style={{ padding: '20px' }}>
      <input
        type="text"
        placeholder="Search for artists..."
        value={searchQuery}
        onChange={(e) => setSearchQuery(e.target.value)}
        style={{ width: '100%', padding: '10px', marginBottom: '20px', fontSize: '16px' }}
      />

      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(160px, 1fr))', gap: 20 }}>
        {artists.map((artist, index) => (
          <div
            key={artist.id || index}
            style={{ cursor: "pointer" }}
            onClick={() => navigate(`/artist/${artist.id}`)}
          >
            <ArtistCard name={artist.name} imageUrl={artist.images?.[0]?.url || artist.imageUrl} />
          </div>
        ))}
      </div>
    </div>
  );
};

export default Dashboard;
