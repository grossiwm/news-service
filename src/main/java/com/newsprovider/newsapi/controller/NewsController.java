package com.newsprovider.newsapi.controller;

import com.newsprovider.newsapi.dto.NewsRequestDTO;
import com.newsprovider.newsapi.service.NewsDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsDataService newsDataService;

    @GetMapping
    public ResponseEntity<Object> getNews(NewsRequestDTO newsRequestDTO) {
        return ResponseEntity.ok(newsDataService.getNews(newsRequestDTO));
    }
}
