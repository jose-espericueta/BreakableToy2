package com.spotifyapp.Spotify_backend.artist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AlbumSummaryResponse {
    private String id;
    private String name;
    private String imageUrl;
}
