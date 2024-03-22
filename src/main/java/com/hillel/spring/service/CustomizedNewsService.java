package com.hillel.spring.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.stereotype.Service;

@Service
public class CustomizedNewsService {

    private final NewsService newsService;

    public CustomizedNewsService(NewsService newsService) {
        this.newsService = newsService;
    }

    public JsonNode getCustomizedNews() {

        JsonNode newsJson = newsService.getNews();

        for (JsonNode article : newsJson.get("articles")) {
            String articleContent = article.get("content").asText();
            String customizedContent = articleContent + ", 'Glory to Ukraine' ";
            ((ObjectNode) article).put("content", customizedContent);
        }
        return newsJson;
    }
}
