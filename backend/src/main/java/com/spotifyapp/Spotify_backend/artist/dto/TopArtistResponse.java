package com.spotifyapp.Spotify_backend.artist.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TopArtistResponse {
    private String id;
    private String name;
    private String imageUrl;

    public TopArtistResponse(String id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

}