import React from 'react';

interface Props {
  name: string;
  imageUrl: string;
}

const ArtistCard: React.FC<Props> = ({ name, imageUrl }) => {
  return (
    <div style={{
      width: 300,
      height: 360,
      border: '1px solid #333',
      padding: 12,
      borderRadius: 8,
      textAlign: 'center',
      backgroundColor: '#181818',
      display: 'flex',
      flexDirection: 'column',
      justifyContent: 'space-between'
    }}>
      <img
        src={imageUrl}
        alt={name}
        style={{
          width: '100%',
          height: '100%',
          objectFit: 'cover',
          borderRadius: 6,
          marginBottom: 8
        }}
      />
      <h3 style={{
        fontSize: 14,
        color: 'white',
        margin: 0,
        whiteSpace: 'nowrap',
        overflow: 'hidden',
        textOverflow: 'ellipsis'
      }}>
        {name}
      </h3>
    </div>
  );
};

export default ArtistCard;