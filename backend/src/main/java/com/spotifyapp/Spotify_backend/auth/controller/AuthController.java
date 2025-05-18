package com.spotifyapp.Spotify_backend.auth.controller;

import com.spotifyapp.Spotify_backend.auth.dto.LoginRedirectResponse;
import com.spotifyapp.Spotify_backend.auth.dto.SpotifyTokenResponse;
import com.spotifyapp.Spotify_backend.artist.dto.TopArtistResponse;
import com.spotifyapp.Spotify_backend.auth.service.OAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final OAuthService oAuthService;

    public AuthController(OAuthService oAuthService) {

        this.oAuthService = oAuthService;
    }

    @GetMapping("/spotify")
    public ResponseEntity<Void> redirectToSpotifyAuth() {
        String spotifyAuthUrl = oAuthService.buildAuthUri();
        return ResponseEntity.status(302)
                .header("Location", spotifyAuthUrl)
                .build();
    }


    @PostMapping("/spotify")
    public ResponseEntity<LoginRedirectResponse> authorizeUser() {
        String url = oAuthService.buildAuthUri();
        return ResponseEntity.ok(new LoginRedirectResponse(url));
    }

    @GetMapping("/callback")
    public RedirectView handleCallback(@RequestParam("code") String code) {
        SpotifyTokenResponse tokenResponse = oAuthService.exchangeCodeForToken(code);

        String redirectUri = "http://localhost:5173?access_token=" + tokenResponse.getAccessToken();
        return new RedirectView(redirectUri);
    }
}
