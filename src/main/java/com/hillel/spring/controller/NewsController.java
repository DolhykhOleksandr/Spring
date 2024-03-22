package com.hillel.spring.controller;

import com.hillel.spring.service.NewsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewsController {

    private final NewsService newsService;


    public NewsController(NewsService newsService) {
        this.newsService = newsService;

    }

    @GetMapping("/news")
    public Object getNews() {
        return newsService.getNews();
    }

}
