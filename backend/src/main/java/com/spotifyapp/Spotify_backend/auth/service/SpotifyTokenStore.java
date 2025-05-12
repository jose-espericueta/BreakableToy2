package com.spotifyapp.Spotify_backend.auth.service;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Getter
@Component
public class SpotifyTokenStore {
    private String accessToken;
    private String refreshToken;
    private Instant expiresAt;

    public void saveTokens(String accessToken, String refreshToken, Long expiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresAt = Instant.now().plusSeconds(expiresIn);
    }

    public boolean isTokenExpired() {
        return expiresAt == null || Instant.now().isAfter(expiresAt);
    }

}
