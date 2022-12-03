package ru.practicum.explore_with_me.stat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.explore_with_me.stat.model.EndpointHit;
import ru.practicum.explore_with_me.stat.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<EndpointHit, Long> {

    @Query(value = "SELECT NEW ru.practicum.explore_with_me.stat.model.ViewStats(e.app, e.uri, COUNT(e.ip)) " +
            "FROM EndpointHit AS e " +
            "WHERE e.timestamp BETWEEN :start AND :end " +
            "AND (COALESCE(:uris, NULL) IS NULL OR e.uri IN :uris) " +
            "GROUP BY e.uri, e.app")
    List<ViewStats> getViewStats(@Param("uris") List<String> uris, @Param("start") LocalDateTime start,
                                 @Param("end") LocalDateTime end);

    @Query(value = "SELECT NEW ru.practicum.explore_with_me.stat.model.ViewStats(e.app, e.uri, COUNT(DISTINCT e.ip)) " +
            "FROM EndpointHit AS e " +
            "WHERE e.timestamp BETWEEN :start AND :end " +
            "AND (COALESCE(:uris, NULL) IS NULL OR e.uri IN :uris) " +
            "GROUP BY e.uri, e.app")
    List<ViewStats> getViewStatsUnique(@Param("uris") List<String> uris, @Param("start") LocalDateTime start,
                                       @Param("end") LocalDateTime end);
}
