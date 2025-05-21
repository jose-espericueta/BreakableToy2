import { useEffect, useState, useContext } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { AuthContext } from "../context/AuthContext";
import './ArtistPage.css';
import ArtistCard from '../components/ArtistCard';

interface ArtistDetail {
  name: string;
  imageUrl: string;
  biography: string;
  genres: string[];
}

interface Album {
  id: string;
  name: string;
  imageUrl: string;
}

export default function ArtistPage() {
  const { id } = useParams();
  const { token } = useContext(AuthContext);
  const [artist, setArtist] = useState<ArtistDetail | null>(null);
  const [albums, setAlbums] = useState<Album[]>([]);
  const navigate = useNavigate();

  useEffect(() => {
    if (!id || !token) return;

    fetch(`http://127.0.0.1:8080/artists/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((res) => res.json())
      .then((data) => setArtist(data))
      .catch((err) => console.error("Failed to load artist", err));
  }, [id, token]);

  useEffect(() => {
    if (!id || !token) return;

    fetch(`http://127.0.0.1:8080/artists/${id}/albums`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((res) => res.json())
      .then((data) => setAlbums(data))
      .catch((err) => console.error("Failed to load albums", err));
  }, [id, token]);

  if (!artist) return <div className="artist-container">Loading artist details...</div>;

  return (
    <div className="artist-container">
      <button onClick={() => history.back()} className="go-back">Go back</button>

      <div className="artist-header">
        {artist.imageUrl && <img src={artist.imageUrl} alt={artist.name} />}
        <div className="artist-info">
          <h1>{artist.name}</h1>
          <p><strong>Genres:</strong> {artist.genres.join(", ")}</p>
          <p>{artist.biography}</p>
        </div>
      </div>

      <div className="album-section">
        <h2>Albums</h2>
        <div className="album-grid">
          {albums.map(album => (
            <div
              key={album.id}
              style={{ cursor: "pointer" }}
              onClick={() => navigate(`/album/${album.id}`)}
            >
              <ArtistCard name={album.name} imageUrl={album.imageUrl} />
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}