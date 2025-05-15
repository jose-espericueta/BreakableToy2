import React from 'react';

interface Props {
    name: string;
    imageUrl: string;
}

const ArtistCard: React.FC<Props> = ({ name, imageUrl }) => {
    return (
        <div style={{ border: '1px solid #ddd', padding: 16, textAlign: 'center', borderRadius: 8}}>
            <img src={imageUrl} alt={name} style={{ width: '100%', borderRadius: 8 }} />
            <h3>{name}</h3>
        </div>
    );
};

export default ArtistCard;