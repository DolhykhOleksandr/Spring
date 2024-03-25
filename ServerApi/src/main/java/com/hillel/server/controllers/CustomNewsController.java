package com.hillel.server.controllers;

import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.Source;
import lombok.RequiredArgsConstructor;
import com.hillel.server.services.RestClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customNews")
@RequiredArgsConstructor
public class CustomNewsController {

    private final RestClient restClient;

    @GetMapping("/getCustomNewsHeaders")
    public ResponseEntity<List<String>> getCustomNewsHeaders() {
        return restClient.getCustomNewsHeaders();
    }

    @GetMapping("/getEverything")
    public ResponseEntity<List<Article>> getEverything() {
        return restClient.getEverything();
    }

    @GetMapping("/getSources")
    public ResponseEntity<List<Source>> getSources() {
        return restClient.getSources();
    }
}