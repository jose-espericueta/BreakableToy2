package com.spotifyapp.Spotify_backend.auth.service;

import com.spotifyapp.Spotify_backend.auth.config.SpotifyOAuthConfig;
import com.spotifyapp.Spotify_backend.auth.dto.SpotifyTokenResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

@Service
public class OAuthService {
    private final SpotifyOAuthConfig config;
    private final RestTemplate restTemplate;
    private final SpotifyTokenStore tokenStore;

    public OAuthService(SpotifyOAuthConfig config, RestTemplate restTemplate, SpotifyTokenStore tokenStore) {
        this.config = config;
        this.restTemplate = restTemplate;
        this.tokenStore = tokenStore;
    }

    public String buildAuthUri() {
        String state = UUID.randomUUID().toString();

        return UriComponentsBuilder
                .fromHttpUrl("https://accounts.spotify.com/authorize")
                .queryParam("client_id", config.getClientId())
                .queryParam("response_type", "code")
                .queryParam("redirect_uri", config.getRedirectUri())
                .queryParam("scope", config.getScopes())
                .queryParam("state", state)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUriString();
    }

    public SpotifyTokenResponse exchangeCodeForToken(String code) {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("redirect_uri", config.getRedirectUri());
        params.add("grant_type", "authorization_code");
        params.add("code", code);

        String credentials = config.getClientId() + ":" + config.getClientSecret();
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Basic " + encodedCredentials);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<SpotifyTokenResponse> response = restTemplate.postForEntity(
                "https://accounts.spotify.com/api/token",
                request,
                SpotifyTokenResponse.class
        );

        tokenStore.saveTokens(
                response.getBody().getAccessToken(),
                response.getBody().getRefreshToken()
        );

        return response.getBody();
    }
}