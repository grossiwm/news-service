package com.newsprovider.newsapi.service;

import com.newsprovider.newsapi.dto.NewsRequestDTO;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RootUriTemplateHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;

@Service
public class NewsDataService {

    @Value("${newsdata.rooturl}")
    private String rootUrl;

    @Value("${newsdata.apikey}")
    private String apikey;

    @Autowired
    private RestTemplate restTemplate;

    public Object getNews(NewsRequestDTO newsRequestDTO) {

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUri(URI.create("/news")).queryParam("apikey", apikey);
        enrich(newsRequestDTO, uriComponentsBuilder);
        URI uri = uriComponentsBuilder.build().toUri();

        return restTemplate.getForObject(uri.toString(), Object.class);
    }

    private void enrich(NewsRequestDTO newsRequestDTO, UriComponentsBuilder uriComponentsBuilder) {
        Arrays.stream(newsRequestDTO.getClass().getDeclaredFields()).forEach(f -> {
            f.setAccessible(true);
            try {
                String value = (String)f.get(newsRequestDTO);

                if (Strings.isNotEmpty(value)) {
                    uriComponentsBuilder.queryParam(f.getName(), value);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
