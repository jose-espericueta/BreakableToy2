package com.spotifyapp.Spotify_backend.auth.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class SpotifyTokenStore {
    private String accessToken;
    private String refreshToken;

    public void saveTokens(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
