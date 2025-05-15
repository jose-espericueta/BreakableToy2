package com.spotifyapp.Spotify_backend.auth.dto;

public class TopArtistResponse {
    private String name;
    private String imageUrl;

    public TopArtistResponse(String name, String imageUrl) {
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}