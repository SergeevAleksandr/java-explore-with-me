package ru.practicum.explore_with_me.stat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explore_with_me.stat.model.EndpointHit;
import ru.practicum.explore_with_me.stat.model.ViewStats;
import ru.practicum.explore_with_me.stat.repository.StatsRepository;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsService {
    private final StatsRepository statsRepository;

    public void create(EndpointHit newHit) {
        statsRepository.save(newHit);
    }

    public List<ViewStats> getViews(List<String> uris, Boolean unique, String start, String end) {
        if (unique) {
            return statsRepository.getViewStatsUnique(uris,
                    LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        } else {
            List<ViewStats> list = statsRepository.getViewStats(uris,
                    LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            return list;
        }
    }
}
