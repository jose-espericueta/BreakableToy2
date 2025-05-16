import { useEffect, useState, useContext } from "react";
import { useParams } from "react-router-dom";
import { AuthContext } from "../context/AuthContext";

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

  if (!album) return <div>Loading...</div>;

  return (
    <div>
      <button onClick={() => history.back()}>Go back</button>
      <h1>{album.name}</h1>
      {album.imageUrl && <img src={album.imageUrl} alt={album.name} width={300} />}
      <p><strong>Release Date:</strong> {album.releaseDate}</p>
      <p><strong>Total Tracks:</strong> {album.totalTracks}</p>
      <h2>Track List</h2>
      <ol>
        {album.trackNames.map((track, index) => (
          <li key={index}>{track}</li>
        ))}
      </ol>
    </div>
  );
}
