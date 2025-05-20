package com.spotifyapp.Spotify_backend.artist.service;

import com.spotifyapp.Spotify_backend.artist.dto.AlbumDetailResponse;
import com.spotifyapp.Spotify_backend.artist.dto.AlbumSummaryResponse;
import com.spotifyapp.Spotify_backend.artist.dto.ArtistDetailResponse;
import com.spotifyapp.Spotify_backend.artist.dto.TopArtistResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArtistService {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<TopArtistResponse> getTopArtists(String accessToken) {
        String url = "https://api.spotify.com/v1/me/top/artists?limit=10";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Map.class
        );

        List<Map<String, Object>> items = (List<Map<String, Object>>) response.getBody().get("items");

        return items.stream().map(item -> {
            String id = (String) item.get("id");
            String name = (String) item.get("name");
            List<Map<String, Object>> images = (List<Map<String, Object>>) item.get("images");
            String imageUrl = images.isEmpty() ? null : (String) images.get(0).get("url");
            return new TopArtistResponse(id, name, imageUrl);
        }).collect(Collectors.toList());
    }

    public ArtistDetailResponse getArtistDetails(String accessToken, String artistId) {

        try {
            String url = "https://api.spotify.com/v1/artists/" + artistId;

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            Map<String, Object> body = response.getBody();

            String name = (String) body.get("name");
            String imageUrl = ((List<Map<String, Object>>) body.get("images")).isEmpty() ? null :
                    (String) ((List<Map<String, Object>>) body.get("images")).get(0).get("url");
            List<String> genres = (List<String>) body.get("genres");

            return new ArtistDetailResponse(name, imageUrl, "Biography not available", genres);
        }catch (Exception e) {
            System.out.println("Error while fetching artist: " + e.getMessage());
            throw new RuntimeException("Error fetching artist details", e);
        }
    }

    public AlbumDetailResponse getAlbumDetails(String accessToken, String albumId) {
        String url = "https://api.spotify.com/v1/albums/" + albumId;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        Map<String, Object> body = response.getBody();

        String name = (String) body.get("name");
        String releaseDate = (String) body.get("release_date");
        int totalTracks = (int) body.get("total_tracks");
        String imageUrl = ((List<Map<String, Object>>) body.get("images")).isEmpty() ? null :
                (String) ((List<Map<String, Object>>) body.get("images")).get(0).get("url");

        List<Map<String, Object>> items = (List<Map<String, Object>>) ((Map<String, Object>) body.get("tracks")).get("items");
        List<String> trackNames = items.stream().map(item -> (String) item.get("name")).collect(Collectors.toList());

        return new AlbumDetailResponse(name, imageUrl, totalTracks, releaseDate, trackNames);
    }

    public List<AlbumSummaryResponse> getAlbumsByArtist(String accessToken, String artistId) {
        String url = "https://api.spotify.com/v1/artists/" + artistId + "/albums?include_groups=album,single&limit=10";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        List<Map<String, Object>> items = (List<Map<String, Object>>) response.getBody().get("items");

        return items.stream().map(item -> {
            String id = (String) item.get("id");
            String name = (String) item.get("name");
            List<Map<String, Object>> images = (List<Map<String, Object>>) item.get("images");
            String imageUrl = images.isEmpty() ? null : (String) images.get(0).get("url");
            return new AlbumSummaryResponse(id, name, imageUrl);
        }).collect(Collectors.toList());
    }

    public Object searchSpotify(String accessToken, String query, String type) {
        String url = "https://api.spotify.com/v1/search?q=" + query + "&type=" + type + "&limit=10";

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
        return response.getBody();
    }


}
