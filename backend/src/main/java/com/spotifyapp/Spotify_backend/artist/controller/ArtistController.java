package com.spotifyapp.Spotify_backend.artist.controller;

import com.spotifyapp.Spotify_backend.artist.dto.AlbumDetailResponse;
import com.spotifyapp.Spotify_backend.artist.dto.AlbumSummaryResponse;
import com.spotifyapp.Spotify_backend.artist.dto.ArtistDetailResponse;
import com.spotifyapp.Spotify_backend.artist.dto.TopArtistResponse;
import com.spotifyapp.Spotify_backend.artist.service.ArtistService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artists")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping("/me/top")
    public ResponseEntity<List<TopArtistResponse>> getTopArtists(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        List<TopArtistResponse> topArtists = artistService.getTopArtists(token);
        return ResponseEntity.ok(topArtists);
    }


    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArtistDetailResponse> getArtistDetails(@RequestHeader("Authorization") String authHeader,
                                                                 @PathVariable String id) {
        String token = authHeader.replace("Bearer ", "");
        ArtistDetailResponse artist = artistService.getArtistDetails(token, id);
        return ResponseEntity.ok(artist);
    }

    @GetMapping(value = "/albums/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AlbumDetailResponse> getAlbumDetails(@RequestHeader("Authorization") String authHeader,
                                                               @PathVariable String id) {
        String token = authHeader.replace("Bearer ", "");
        AlbumDetailResponse album = artistService.getAlbumDetails(token, id);
        return ResponseEntity.ok(album);
    }

    @GetMapping(value = "/{id}/albums", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AlbumSummaryResponse>> getAlbumsByArtist(
            @RequestHeader("Authorization") String authHeader,
            @PathVariable String id) {
        String token = authHeader.replace("Bearer ", "");
        List<AlbumSummaryResponse> albums = artistService.getAlbumsByArtist(token, id);
        return ResponseEntity.ok(albums);
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> searchSpotify(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam("q") String query,
            @RequestParam("type") String type) {

        String token = authHeader.replace("Bearer ", "");
        Object result = artistService.searchSpotify(token, query, type);
        return ResponseEntity.ok(result);
    }

}
