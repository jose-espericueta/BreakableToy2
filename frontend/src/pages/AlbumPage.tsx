import { useEffect, useState, useContext } from "react";
import { useParams } from "react-router-dom";
import { AuthContext } from "../context/AuthContext";
import './AlbumPage.css';

interface AlbumDetail {
  name: string;
  imageUrl: string;
  totalTracks: number;
  releaseDate: string;
  trackNames: string[];
}

export default function AlbumPage() {
  const { id } = useParams();
  const { token } = useContext(AuthContext);
  const [album, setAlbum] = useState<AlbumDetail | null>(null);

  useEffect(() => {
    if (!id || !token) return;

    fetch(`http://127.0.0.1:8080/artists/albums/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((res) => res.json())
      .then((data) => setAlbum(data))
      .catch((err) => console.error("Failed to load album", err));
  }, [id, token]);

  if (!album) return <div className="album-container">Loading...</div>;

  return (
    <div className="album-container">
      <button className="go-back" onClick={() => history.back()}>Go back</button>

      <div className="album-header">
        <img src={album.imageUrl} alt={album.name} className="album-image" />
        <div className="album-info">
          <h1 className="album-title">{album.name}</h1>
          <p><strong>Release Date:</strong> {album.releaseDate}</p>
          <p><strong>Total Tracks:</strong> {album.totalTracks}</p>
        </div>
      </div>

      <h2 className="tracklist-title">Track List</h2>
      <ol className="track-list">
        {album.trackNames.map((track, index) => (
          <li key={index} className="track-item">
            <span className="track-index">{index + 1}</span>
            <span className="track-name">{track}</span>
          </li>
        ))}
      </ol>
    </div>
  );
}