package com.hillel.spring.controller;

import com.hillel.spring.service.CustomizedNewsService;
import com.hillel.spring.service.NewsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewsController {

    private final NewsService newsService;
    private final CustomizedNewsService customizedNewsService;

    public NewsController(NewsService newsService, CustomizedNewsService customizedNewsService) {
        this.newsService = newsService;
        this.customizedNewsService = customizedNewsService;
    }

    @GetMapping("/news")
    public Object getNews() {
        return newsService.getNews();
    }

    @GetMapping("/customized-news")
    public Object getCustomizedNews() {
        return customizedNewsService.getCustomizedNews();
    }
}