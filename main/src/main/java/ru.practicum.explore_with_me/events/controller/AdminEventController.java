package ru.practicum.explore_with_me.events.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.events.model.AdminEventParameters;
import ru.practicum.explore_with_me.events.model.dto.AdminUpdateEventRequest;
import ru.practicum.explore_with_me.events.model.dto.EventFullDto;
import ru.practicum.explore_with_me.events.service.EventService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
public class AdminEventController {

    final String eventIdString = "/{eventId}";
    private final EventService eventsService;

    @GetMapping()
    public List<EventFullDto> getEvents(@RequestParam(name = "users",required = false) List<Long> users,
                                        @RequestParam(name = "categories",required = false) List<Long> categories,
                                        @RequestParam(name = "states",required = false) List<String> states,
                                        @RequestParam(name = "rangeStart",required = false) String rangeStart,
                                        @RequestParam(name = "rangeEnd",required = false) String rangeEnd,
                                        @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                        @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        AdminEventParameters params = new AdminEventParameters(users, categories, rangeStart, rangeEnd, states, from, size);
        log.info("Поиск событий с параметрами администратора");
        return eventsService.getAdminEvents(params);
    }

    @PutMapping(value = eventIdString)
    public EventFullDto updateEvent(@PathVariable("eventId") Long eventId,
                                    @RequestBody AdminUpdateEventRequest updateRequest) {
        log.info("Редактирование события id - {} Администратором", eventId);
        return eventsService.updateAdminEvent(eventId, updateRequest);
    }

    @PatchMapping(value = eventIdString + "/publish")
    public EventFullDto setPublished(@PathVariable("eventId") Long eventId) {
        log.info("Администратор публекуеет событие - {}", eventId);
        return eventsService.setPublished(eventId);
    }

    @PatchMapping(value = eventIdString + "/reject")
    public EventFullDto setRejected(@PathVariable("eventId") Long eventId) {
        log.info("Администратор отменяет событие - {}", eventId);
        return eventsService.setRejected(eventId);
    }
}