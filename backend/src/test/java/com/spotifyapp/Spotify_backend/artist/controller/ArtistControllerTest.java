package com.spotifyapp.Spotify_backend.artist.controller;

import com.spotifyapp.Spotify_backend.artist.service.ArtistService;
import com.spotifyapp.Spotify_backend.artist.dto.ArtistDetailResponse;
import com.spotifyapp.Spotify_backend.artist.dto.AlbumDetailResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ArtistController.class)
public class ArtistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ArtistService artistService;

    @Test
    public void testGetArtistDetails() throws Exception {
        ArtistDetailResponse mockArtist = new ArtistDetailResponse(
                "Test Artist",
                "http://image.url",
                "Biography not available",
                List.of("rock", "pop")
        );

        when(artistService.getArtistDetails("valid-token", "123")).thenReturn(mockArtist);

        mockMvc.perform(get("/artists/123")
                        .header("Authorization", "Bearer valid-token")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Artist"))
                .andExpect(jsonPath("$.genres[0]").value("rock"));
    }

    @Test
    public void testGetAlbumDetails() throws Exception {
        AlbumDetailResponse mockAlbum = new AlbumDetailResponse(
                "Mock Album",
                "http://image.url",
                10,
                "2024-01-01",
                Arrays.asList("Track 1", "Track 2")
        );

        when(artistService.getAlbumDetails("valid-token", "abc")).thenReturn(mockAlbum);

        mockMvc.perform(get("/artists/albums/abc")
                        .header("Authorization", "Bearer valid-token")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mock Album"))
                .andExpect(jsonPath("$.trackNames[0]").value("Track 1"));
    }
}
