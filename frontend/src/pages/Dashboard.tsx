import { useEffect, useState, useContext } from 'react';
import ArtistCard from '../components/ArtistCard';
import { AuthContext } from '../context/AuthContext';
import { useNavigate } from 'react-router-dom';

const Dashboard = () => {
  const { token } = useContext(AuthContext);
  const [results, setResults] = useState<any[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [searchQuery, setSearchQuery] = useState('');
  const [debouncedQuery, setDebouncedQuery] = useState('');
  const [searchType, setSearchType] = useState('artist');
  const navigate = useNavigate();

  useEffect(() => {
    const handler = setTimeout(() => {
      setDebouncedQuery(searchQuery);
    }, 1000);
    return () => clearTimeout(handler);
  }, [searchQuery]);

  useEffect(() => {
    if (!token) return;

    const endpoint = debouncedQuery
      ? `http://127.0.0.1:8080/artists/search?q=${debouncedQuery}&type=${searchType}`
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
        let result = [];
        if (debouncedQuery) {
          if (searchType === 'artist') result = data.artists?.items ?? [];
          else if (searchType === 'album') result = data.albums?.items ?? [];
          else if (searchType === 'track') result = data.tracks?.items ?? [];
        } else {
          result = data;
        }
        setResults(result);
      })
      .catch(err => setError(err.message))
      .finally(() => setLoading(false));
  }, [token, debouncedQuery, searchType]);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error: {error}</p>;
  if (results.length === 0) return <p>No results found</p>;

  return (
    <div style={{ padding: '20px' }}>
      <div style={{ marginBottom: '10px' }}>
        <label style={{ marginRight: '10px' }}>Search type:</label>
        <select
          value={searchType}
          onChange={(e) => setSearchType(e.target.value)}
          style={{ padding: '5px' }}
        >
          <option value="artist">Artist</option>
          <option value="album">Album</option>
          <option value="track">Track</option>
        </select>
      </div>

      <input
        type="text"
        placeholder={`Search for ${searchType}s...`}
        value={searchQuery}
        onChange={(e) => setSearchQuery(e.target.value)}
        style={{ width: '100%', padding: '10px', marginBottom: '20px', fontSize: '16px' }}
      />

      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fit, minmax(160px, 1fr))', gap: 20 }}>
        {results.map((item, index) => (
          <div
            key={item.id || index}
            style={{ cursor: "pointer" }}
            onClick={() => {
              if (searchType === 'artist') navigate(`/artist/${item.id}`);
              else if (searchType === 'album') navigate(`/album/${item.id}`);
              else if (searchType === 'track') {
                fetch(`https://api.spotify.com/v1/tracks/${item.id}`, {
                      headers: {
                        Authorization: `Bearer ${token}`
                      }
                    })
                      .then(res => res.json())
                      .then(data => {
                          console.log("preview_url: ", data.preview_url);
                        if (data.preview_url) {
                          const audio = new Audio(data.preview_url);
                          audio.play();
                        } else {
                          alert('No preview available for this track.');
                        }
                      });
              }
            }}
          >
            <ArtistCard
              name={item.name}
              imageUrl={
                item.images?.[0]?.url || item.album?.images?.[0]?.url || item.imageUrl
              }
            />
          </div>
        ))}
      </div>
    </div>
  );
};

export default Dashboard;