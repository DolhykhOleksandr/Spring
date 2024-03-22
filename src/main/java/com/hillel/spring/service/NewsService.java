package com.hillel.spring.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NewsService {
    private final RestTemplate restTemplate;
    private static final String apiKey = "34e97cf36d514220ace2012979fcb064";


    public NewsService(RestTemplate restTemplate) {

        this.restTemplate = restTemplate;
    }

    public JsonNode getNews() {
        String url = "https://newsapi.org/v2/top-headlines?sources=bbc-news&apiKey=" + apiKey;
        String newsJsonString = restTemplate.getForObject(url, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readTree(newsJsonString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
