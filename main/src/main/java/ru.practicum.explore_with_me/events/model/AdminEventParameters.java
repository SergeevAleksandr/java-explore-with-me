package ru.practicum.explore_with_me.events.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import ru.practicum.explore_with_me.events.model.enums.EventStateEnum;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@FieldDefaults(makeFinal = false, level = AccessLevel.PRIVATE)
public class AdminEventParameters {

    List<Long> users;
    List<Long> categories;
    LocalDateTime rangeStart;
    LocalDateTime rangeEnd;
    List<EventStateEnum> states;
    PageRequest pageRequest;

    public AdminEventParameters(List<Long> users, List<Long> categories, String rangeStart,
                                String rangeEnd, List<String> states, Integer from, Integer size) {
        this.users = users;
        this.categories = categories;
        if (rangeStart != null && rangeEnd != null) {
            this.rangeStart = LocalDateTime.parse(rangeStart, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            this.rangeEnd = LocalDateTime.parse(rangeEnd, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } else {
            this.rangeStart = LocalDateTime.now();
            this.rangeEnd = LocalDateTime.now().plusYears(20);
        }
        if (states != null) {
            this.states = states.stream().map(EventStateEnum::valueOf).collect(Collectors.toList());
        }
        int page = from / size;
        this.pageRequest = PageRequest.of(page, size);
    }
}