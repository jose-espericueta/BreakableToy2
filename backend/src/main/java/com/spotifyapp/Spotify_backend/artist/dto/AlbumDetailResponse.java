package com.spotifyapp.Spotify_backend.artist.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AlbumDetailResponse {
    private String name;
    private String imageUrl;
    private int totalTracks;
    private String releaseDate;
    private List<String> trackNames;
}
