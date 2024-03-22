package com.hillel.spring.controller;

import com.hillel.spring.service.CustomizedNewsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomizedNewsController {
    private final CustomizedNewsService customizedNewsService;

    public CustomizedNewsController(CustomizedNewsService customizedNewsService) {
        this.customizedNewsService = customizedNewsService;
    }

    @GetMapping("/customized-news")
    public Object getCustomizedNews() {
        return customizedNewsService.getCustomizedNews();
    }
}
