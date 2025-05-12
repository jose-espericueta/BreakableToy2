package com.spotifyapp.Spotify_backend.auth.service;

import com.spotifyapp.Spotify_backend.auth.config.SpotifyOAuthConfig;
import com.spotifyapp.Spotify_backend.auth.dto.SpotifyTokenResponse;
import com.spotifyapp.Spotify_backend.auth.interceptor.SpotifyAuthInterceptor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

@Service
public class OAuthService {
    private final SpotifyOAuthConfig config;
    private final SpotifyTokenStore tokenStore;

    public OAuthService(SpotifyOAuthConfig config, SpotifyTokenStore tokenStore) {
        this.config = config;
        this.tokenStore = tokenStore;
    }

    public String buildAuthUri() {
        String state = UUID.randomUUID().toString();

        return UriComponentsBuilder.fromUriString("https://accounts.spotify.com/authorize")
                .queryParam("client_id", config.getClientId())
                .queryParam("response_type", "code")
                .queryParam("redirect_uri", config.getRedirectUri())
                .queryParam("scope", config.getScopes())
                .queryParam("state", state)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUriString();
    }

    private RestTemplate createAuthorizedRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new SpotifyAuthInterceptor(tokenStore, this));
        return restTemplate;
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

        ResponseEntity<SpotifyTokenResponse> response = createAuthorizedRestTemplate().postForEntity (
                "https://accounts.spotify.com/api/token",
                request,
                SpotifyTokenResponse.class
        );

        tokenStore.saveTokens(
                Objects.requireNonNull(response.getBody()).getAccessToken(),
                response.getBody().getRefreshToken(),
                response.getBody().getExpiresIn()
        );

        return response.getBody();
    }

    public void refreshAccessToken() {
        String refreshToken = tokenStore.getRefreshToken();

        if (refreshToken == null) {
            throw new IllegalStateException("No refresh token available");
        }

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "refresh_token");
        params.add("refresh_token", refreshToken);

        String credentials = config.getClientId() + ":" + config.getClientSecret();
        String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.UTF_8));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Basic " + encodedCredentials);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<SpotifyTokenResponse> response = createAuthorizedRestTemplate().postForEntity(
                "https://accounts.spotify.com/api/token",
                request,
                SpotifyTokenResponse.class
        );

        SpotifyTokenResponse tokenResponse = Objects.requireNonNull(response.getBody());

        tokenStore.saveTokens(
                tokenResponse.getAccessToken(),
                tokenStore.getRefreshToken(),
                tokenResponse.getExpiresIn()
        );
    }

    public String getValidAccessToken() {
        if(tokenStore.isTokenExpired()) {
            refreshAccessToken();
        }

        return tokenStore.getAccessToken();
    }
}