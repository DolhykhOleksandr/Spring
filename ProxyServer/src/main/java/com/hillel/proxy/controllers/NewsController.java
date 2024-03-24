package com.hillel.proxy.controllers;

import com.hillel.proxy.services.NewsService;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.Source;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @GetMapping("/getEverything")
    public ResponseEntity<List<Article>> getEverything() {
        return ResponseEntity.ok(newsService.getEverything());
    }

    @GetMapping("/getHeaders")
    public ResponseEntity<List<String>> getTopHeadlines() {
        return ResponseEntity.ok(newsService.getTopHeadlines());
    }

    @GetMapping("/getSources")
    public ResponseEntity<List<Source>> getSources() {
        return ResponseEntity.ok(newsService.getSources());
    }
}


