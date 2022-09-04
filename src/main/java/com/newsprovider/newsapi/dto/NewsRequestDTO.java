package com.newsprovider.newsapi.dto;

public record NewsRequestDTO(String language, String country, String category, String keywords, String page) {}
