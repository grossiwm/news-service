package com.newsprovider.newsapi.dto;

import java.util.List;

public record NewsRequestDTO(String language, String country, List<String> category, String keywords, String page) {}
