package com.hillel.server.services;

import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.Source;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    private static final String GET_EVERYTHING_URL = "http://localhost:8080/news/getEverything";
    private static final String GET_TOP_HEADERS_URL = "http://localhost:8080/news/getHeaders";
    private static final String GET_SOURCES_URL = "http://localhost:8080/news/getSources";

    public ResponseEntity<List<Article>> getEverything() {
        ParameterizedTypeReference<List<Article>> responseType = new ParameterizedTypeReference<>() {};
        try {
            return restTemplate.exchange(GET_EVERYTHING_URL, HttpMethod.GET, null, responseType);
        } catch (Exception e) {
            log.error("An error occurred while fetching everything: {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<List<String>> getCustomNewsHeaders() {
        ParameterizedTypeReference<List<String>> responseType = new ParameterizedTypeReference<>() {};
        try {
            return restTemplate.exchange(GET_TOP_HEADERS_URL, HttpMethod.GET, null, responseType);
        } catch (Exception e) {
            log.error("An error occurred while fetching custom news headers: {}", e.getMessage());
            throw e;
        }
    }

    public ResponseEntity<List<Source>> getSources() {
        ParameterizedTypeReference<List<Source>> responseType = new ParameterizedTypeReference<>() {};
        try {
            return restTemplate.exchange(GET_SOURCES_URL, HttpMethod.GET, null, responseType);
        } catch (Exception e) {
            log.error("An error occurred while fetching sources: {}", e.getMessage());
            throw e;
        }
    }
}
