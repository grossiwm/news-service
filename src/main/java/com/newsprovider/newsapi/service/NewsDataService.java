package com.newsprovider.newsapi.service;

import com.newsprovider.newsapi.dto.NewsRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class NewsDataService {

    @Value("${newsData.rootUrl}")
    private String rootUrl;

    @Value("${newsData.apiKey}")
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
                Object value = f.get(newsRequestDTO);

                if (!Objects.isNull(value) &&
                        (
                                (value instanceof List && !((List) value).isEmpty())
                        || (value instanceof String && !((String) value).isEmpty())
                        )
                )
                uriComponentsBuilder.queryParam(f.getName(), format(value));

            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private String format(Object object) {
        if (object instanceof String)
            return (String) object;

        List<String> list = (List<String>) object;
        return String.join(",", list.toArray(new String[list.size()]));
    }

}
