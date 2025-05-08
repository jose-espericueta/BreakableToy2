package com.spotifyapp.Spotify_backend.auth.service;

import com.spotifyapp.Spotify_backend.auth.config.SpotifyOAuthConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

class OAuthServiceTest {

    private OAuthService oAuthService;

    @BeforeEach
    void setUp() {
        SpotifyOAuthConfig mockConfig = Mockito.mock(SpotifyOAuthConfig.class);
        SpotifyTokenStore tokenStore = new SpotifyTokenStore();
        RestTemplate restTemplate = new RestTemplate();

        Mockito.when(mockConfig.getClientId()).thenReturn("mock-client-id");
        Mockito.when(mockConfig.getClientSecret()).thenReturn("mock-secret");
        Mockito.when(mockConfig.getRedirectUri()).thenReturn("http://127.0.0.1:8080/auth/callback");
        Mockito.when(mockConfig.getScopes()).thenReturn("user-read-private user-read-email");

        oAuthService = new OAuthService(mockConfig, restTemplate, tokenStore);
    }

    @Test
    void testBuildAuthUriContainsCorrectParameters() {
        String url = oAuthService.buildAuthUri();

        System.out.println("Generated URL: " + url);

        assertTrue(url.contains("client_id=mock-client-id"));
        assertTrue(url.contains("redirect_uri=http://127.0.0.1:8080/auth/callback"));
        assertTrue(url.contains("response_type=code"));
        assertTrue(url.contains("scope=user-read-private%20user-read-email"));
        assertTrue(url.startsWith("https://accounts.spotify.com/authorize?"));
    }
}