package ru.practicum.explore_with_me.stat.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.stat.model.dto.EndpointHitDto;
import ru.practicum.explore_with_me.stat.model.ViewStats;
import ru.practicum.explore_with_me.stat.model.mapper.HitMapper;
import ru.practicum.explore_with_me.stat.service.StatsService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@Validated
public class StatsController {
    private final StatsService statsService;

    @PostMapping(value = "/hit")
    public void hit(@Validated @RequestBody EndpointHitDto newHit) {
        log.info("сохрание данных о просмотре ip: {}, app: {}, uri: {}", newHit.getIp(),
                newHit.getApp(), newHit.getUri());
        statsService.create(HitMapper.toEndpointHit(newHit));
    }

    @GetMapping(value = "/stats")
    public List<ViewStats> getViews(@RequestParam(name = "uris") List<String> uris,
                                    @RequestParam(name = "unique") Boolean unique,
                                    @RequestParam(name = "start") String start,
                                    @RequestParam(name = "end") String end) {
        log.info("получение данных о просмотрах");
        return statsService.getViews(uris, unique, start, end);
    }
}
