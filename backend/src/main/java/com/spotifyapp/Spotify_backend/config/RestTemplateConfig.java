package com.spotifyapp.Spotify_backend.config;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder, ObjectProvider<com.spotifyapp.Spotify_backend.auth.service.OAuthService> oAuthServiceProvider) {
        RestTemplate restTemplate = builder.build();

        ClientHttpRequestInterceptor retryInterceptor = (request, body, execution) -> {
            ClientHttpResponse response = execution.execute(request, body);

            if (response.getStatusCode().value() == 401) {
                com.spotifyapp.Spotify_backend.auth.service.OAuthService oAuthService = oAuthServiceProvider.getIfAvailable();
                if (oAuthService != null) {
                    oAuthService.refreshAccessToken();
                }

                return execution.execute(request, body);
            }

            return response;
        };

        restTemplate.setInterceptors(Collections.singletonList(retryInterceptor));
        return restTemplate;
    }
}
