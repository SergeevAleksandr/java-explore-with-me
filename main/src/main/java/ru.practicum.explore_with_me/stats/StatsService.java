package ru.practicum.explore_with_me.stats;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.explore_with_me.client.EventClient;
import ru.practicum.explore_with_me.events.model.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StatsService {
    private final EventClient eventClient;

    public void hit(String url, String ip) {
        eventClient.hit(url, ip);
    }

    public Map<Long, Long> getViews(List<Event> eventList, Boolean unique) {
        if (eventList.size() == 0) {
            return new HashMap<>();
        }
        List<String> uriList = new ArrayList<>();
        Map<String, Long> urisMap = new HashMap<>();
        for (Event event : eventList) {
            uriList.add("/event/" + event.getId());
            urisMap.put("/event/" + event.getId(), event.getId());
        }
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = eventList.stream().min(Comparator.comparing(Event::getCreatedOn)).get().getCreatedOn();
        ResponseEntity<Object> viewsList = eventClient.getViews(uriList,
                startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                unique);
        List<StatsDto> statsList = (List<StatsDto>) viewsList.getBody();
        Map<Long, Long> viewsMap = new HashMap<>();
        if (statsList != null) {
            for (StatsDto statsDto : statsList) {
                viewsMap.put(urisMap.get(statsDto.getUri()), statsDto.getHits());
            }
        }
        return viewsMap;
    }

    public Long getView(Event event, Boolean unique) {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = event.getCreatedOn();
        ResponseEntity<Object> viewsList = eventClient.getViews(List.of("/event/" + event.getId()),
                startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                unique);
        List<StatsDto> statsList = (List<StatsDto>) viewsList.getBody();
        if (statsList != null && statsList.size() > 0) {
            return statsList.get(0).getHits();
        } else {
            return 0L;
        }
    }
}
