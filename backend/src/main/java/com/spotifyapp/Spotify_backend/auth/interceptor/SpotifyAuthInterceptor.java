package com.spotifyapp.Spotify_backend.auth.interceptor;

import com.spotifyapp.Spotify_backend.auth.service.SpotifyTokenStore;
import com.spotifyapp.Spotify_backend.auth.service.OAuthService;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class SpotifyAuthInterceptor implements ClientHttpRequestInterceptor {

    private final SpotifyTokenStore tokenStore;
    private final OAuthService authService;

    public SpotifyAuthInterceptor(SpotifyTokenStore tokenStore, OAuthService authService) {
        this.tokenStore = tokenStore;
        this.authService = authService;
    }

    @Override
    public ClientHttpResponse intercept(
            org.springframework.http.HttpRequest request,
            byte[] body,
            ClientHttpRequestExecution execution) throws IOException {

        ClientHttpResponse response = execution.execute(request, body);

        if (response.getStatusCode().value() == 401) {
            authService.refreshAccessToken();
            response = execution.execute(request, body);
        }

        return response;
    }
}
