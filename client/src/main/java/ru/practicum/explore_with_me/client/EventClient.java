package ru.practicum.explore_with_me.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class EventClient extends BaseClient {
    @Autowired
    public EventClient(@Value("${stat-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build()
        );
    }

    public ResponseEntity<Object> hit(String uri, String ip) {
       return post("/hit",new EndpointHitDto("main-service",uri,ip,
               LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
    }

    public ResponseEntity<Object> getViews(List<String> urlList,  String startTime, String endTime,Boolean unique) {
        StringBuilder str = new StringBuilder();
        for (String uri : urlList) {
            str.append("uris=").append(uri).append("&");
        }
        Map<String, Object> parameters = Map.of(
                "unique", unique,
                "start", startTime,
                "end", endTime
        );
        return get("/stats?" + str + "unique={unique}&start={start}&end={end}", parameters);
    }
}
