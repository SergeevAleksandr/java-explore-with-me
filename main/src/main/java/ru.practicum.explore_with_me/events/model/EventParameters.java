package ru.practicum.explore_with_me.events.model;


import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.practicum.explore_with_me.events.model.enums.EventSortEnum;
import ru.practicum.explore_with_me.exception.ObjectNotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Data
public class EventParameters {
    private String text;
    private List<Long> categories;
    private Boolean paid;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private Boolean onlyAvailable;
    private PageRequest pageRequest;
    private EventSortEnum sort;

    public EventParameters(String text, Long[] categories, Boolean paid, String rangeStart,
                       String rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size) {
        this.text = text;
        this.categories = Arrays.asList(categories);
        this.paid = paid;
        if (rangeStart != null) {
            this.rangeStart = LocalDateTime.parse(rangeStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } else {
            this.rangeStart = LocalDateTime.now();
        }
        if (rangeEnd != null) {
            this.rangeEnd = LocalDateTime.parse(rangeEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } else {
            this.rangeEnd = LocalDateTime.now().plusHours(20);;
        }
        this.onlyAvailable = onlyAvailable;
        if (sort != null) {
            try {
                this.sort = EventSortEnum.valueOf(sort);
                int page = from / size;
                if (this.sort.equals(EventSortEnum.EVENT_DATE)) {
                    this.pageRequest = PageRequest.of(page, size, Sort.by("date"));
                } else {
                    this.pageRequest = PageRequest.of(page, size);
                }
            } catch (IllegalArgumentException e) {
                throw new ObjectNotFoundException("Неизвестная сортировка " + sort);
            }
        }

    }
}
