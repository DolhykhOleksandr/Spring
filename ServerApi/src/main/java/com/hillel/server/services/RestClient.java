package com.hillel.server.services;

import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.Source;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestClient {


    private final RestTemplate restTemplate;


    @Value("${news.api.get-everything-url}")
    private String getEverythingUrl;

    @Value("${news.api.get-top-headers-url}")
    private String getTopHeadersUrl;

    @Value("${news.api.get-sources-url}")
    private String getSourcesUrl;


    public ResponseEntity<List<Article>> getEverything() {
        ParameterizedTypeReference<List<Article>> responseType = new ParameterizedTypeReference<>() {};
        try {
            return restTemplate.exchange(getEverythingUrl, HttpMethod.GET, null, responseType);
        } catch (Exception e) {
            log.error("An error occurred while fetching everything: {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<List<String>> getCustomNewsHeaders() {
        ParameterizedTypeReference<List<String>> responseType = new ParameterizedTypeReference<>() {};
        try {
            return restTemplate.exchange(getTopHeadersUrl, HttpMethod.GET, null, responseType);
        } catch (Exception e) {
            log.error("An error occurred while fetching custom news headers: {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<List<Source>> getSources() {
        ParameterizedTypeReference<List<Source>> responseType = new ParameterizedTypeReference<>() {};
        try {
            return restTemplate.exchange(getSourcesUrl, HttpMethod.GET, null, responseType);
        } catch (Exception e) {
            log.error("An error occurred while fetching sources: {}", e.getMessage());
            throw e;
        }
    }
}
