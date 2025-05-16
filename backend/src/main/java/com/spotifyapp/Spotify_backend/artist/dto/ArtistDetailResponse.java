package com.spotifyapp.Spotify_backend.artist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArtistDetailResponse {
    private String name;
    private String imageUrl;
    private String biography;
    private List<String> genres;
}
