import { useEffect, useState, useContext } from "react";
import { useParams } from "react-router-dom";
import { AuthContext } from "../context/AuthContext";
import { useNavigate } from 'react-router-dom';

interface ArtistDetail {
  name: string;
  imageUrl: string;
  biography: string;
  genres: string[];
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
      if(!id || !token) return;

      fetch(`http://127.0.0.1:8080/artists/${id}/albums`, {
          headers: {
              Authorization: `Bearer ${token}`,
          },
      })
          .then((res) => res.json())
          .then((data) => setAlbums(data))
          .catch((err) => console.error("Failed to load albums", err));
      }, [id, token]);

  if (!artist) return <div>Loading artists details...</div>;

  return (
    <div>
      <button onClick={() => history.back()}>Go back</button>
      <h1>{artist.name}</h1>
      {artist.imageUrl && <img src={artist.imageUrl} alt={artist.name} width={300} />}
      <p><strong>Genres:</strong> {artist.genres.join(", ")}</p>
      <p>{artist.biography}</p>

      <h2>Albums</h2>
        <div style={{ display: 'flex', flexWrap: 'wrap', gap: '20px' }}>
            {albums.map(album => (
                <div
                    key={album.id}
                    style={{ cursor: "pointer", border: "1px solid #ccc", padding: "10px" }}
                    onClick={() => navigate(`/album/${album.id}`)}
                >
                    <img src={album.imageUrl} alt={album.name} width={150} />
                    <p>{album.name}</p>
                </div>
            ))}
        </div>
      </div>
    );
}
