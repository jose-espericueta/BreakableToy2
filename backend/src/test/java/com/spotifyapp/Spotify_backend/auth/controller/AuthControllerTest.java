package com.spotifyapp.Spotify_backend.auth.controller;

import com.spotifyapp.Spotify_backend.auth.dto.SpotifyTokenResponse;
import com.spotifyapp.Spotify_backend.auth.service.OAuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OAuthService oAuthService;

    @Test
    void testAuthorizeUser_ReturnsRedirectUrl() throws Exception {
        String mockRedirectUrl = "https://accounts.spotify.com/authorize?client_id=mock-client-id";

        when(oAuthService.buildAuthUri()).thenReturn(mockRedirectUrl);

        mockMvc.perform(post("/auth/spotify"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.redirectUrl").value(mockRedirectUrl));
    }

//    @Test
//    void testHandleCallback_ReturnTokenResponse() throws Exception {
//
//        SpotifyTokenResponse mockTokenResponse = new SpotifyTokenResponse();
//        mockTokenResponse.setAccessToken("mock-access-token");
//        mockTokenResponse.setRefreshToken("mock-refresh-token");
//        mockTokenResponse.setTokenType("Bearer");
//        mockTokenResponse.setExpiresIn(3600L);
//        mockTokenResponse.setScope("user-read-private user-read-email");
//
//        when(oAuthService.exchangeCodeForToken("valid-code")).thenReturn(mockTokenResponse);
//
//        mockMvc.perform(get("/auth/callback")
//                .param("code", "valid-code"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.access_token").value("mock-access-token"))
//                .andExpect(jsonPath("$.refresh_token").value("mock-refresh-token"))
//                .andExpect(jsonPath("$.token_type").value("Bearer"))
//                .andExpect(jsonPath("$.expires_in").value(3600))
//                .andExpect(jsonPath("$.scope").value("user-read-private user-read-email"));
//
//    }

//    @Test
//    void shouldReturnTopArtists() throws Exception {
//        mockMvc.perform(get("/artists/me/top")
//                .header("Authorization", "Bearer dummy_token"))
//                .andExpect(status().isOk());
//
//    }
}
