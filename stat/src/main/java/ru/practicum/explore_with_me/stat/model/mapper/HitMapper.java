package ru.practicum.explore_with_me.stat.model.mapper;

import ru.practicum.explore_with_me.stat.model.EndpointHit;
import ru.practicum.explore_with_me.stat.model.dto.EndpointHitDto;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HitMapper {
    public static EndpointHit toEndpointHit(EndpointHitDto hit) {
        return new EndpointHit(
                0,
                hit.getApp(),
                hit.getUri(),
                hit.getIp(),
                LocalDateTime.parse(hit.getTimestamp(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        );
    }
}
