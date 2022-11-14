package ru.practicum.explore_with_me.events.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.events.model.dto.AdminUpdateEventRequest;
import ru.practicum.explore_with_me.events.model.dto.EventFullDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/events")
@RequiredArgsConstructor
public class AdminEventController {
    @GetMapping()
    public List<EventFullDto> getEvents(@RequestParam(name = "users") List<Long> users,
                                        @RequestParam(name = "categories") List<Long> categories,
                                        @RequestParam(name = "states") List<String> states,
                                        @RequestParam(name = "rangeStart") String rangeStart,
                                        @RequestParam(name = "rangeEnd") String rangeEnd,
                                        @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                        @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {

        return null;
    }

    @PutMapping(value = "/{eventId}")
    public EventFullDto updateEvent(@PathVariable("eventId") Long eventId,
                                    @RequestBody AdminUpdateEventRequest updateRequest) {

        return null;
    }

    @PatchMapping(value = "/{eventId}/publish")
    public EventFullDto setPublished(@PathVariable("eventId") Long eventId) {

        return null;
    }

    @PatchMapping(value = "/{eventId}/reject")
    public EventFullDto setRejected(@PathVariable("eventId") Long eventId) {

        return null;
    }
}