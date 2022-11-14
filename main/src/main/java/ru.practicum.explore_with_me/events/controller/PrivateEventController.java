package ru.practicum.explore_with_me.events.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.events.model.dto.EventFullDto;
import ru.practicum.explore_with_me.events.model.dto.EventShortDto;
import ru.practicum.explore_with_me.events.model.dto.NewEventDto;
import ru.practicum.explore_with_me.events.model.dto.UpdateEventRequest;
import ru.practicum.explore_with_me.requests.dto.ParticipationRequestDto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/users/{id}/events")
@RequiredArgsConstructor
public class PrivateEventController {
    @GetMapping()
    public List<EventShortDto> getAllUserEvents(@PathVariable("id") Long id,
                                              @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                              @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return null;
    }
    @PatchMapping()
    public EventFullDto updateEvent(@PathVariable("id") Long id,
                                    @RequestBody UpdateEventRequest updateRequest){

        return null;
    }
    @PostMapping()
    public EventFullDto createEvent(@PathVariable("id") Long id,
                                    @RequestBody NewEventDto newEvent){

        return null;
    }
    @GetMapping(value = "/{eventId}")
    public EventFullDto getUserEventForId(@PathVariable("id") Long id,
                                       @PathVariable("eventId") Long eventId) {

        return null;
    }

    @PatchMapping(value = "/{eventId}")
    public EventFullDto setCancelEvent(@PathVariable("id") Long id,
                                         @PathVariable("eventId") Long eventId) {

        return null;
    }

    @GetMapping(value = "/{eventId}/requests")
    public List<ParticipationRequestDto> getRequestsInUserEvent(@PathVariable("id") Long id,
                                                                @PathVariable("eventId") Long eventId) {
        return null;
    }

    @PatchMapping(value = "/{eventId}/requests/{reqId}/confirm")
    public ParticipationRequestDto setRequestConfirmed(@PathVariable("id") Long id,
                                                       @PathVariable("eventId") Long eventId,
                                                       @PathVariable("reqId") Long reqId) {
        return null;
    }

    @PatchMapping(value = "/{eventId}/requests/{reqId}/reject")
    public ParticipationRequestDto setRequestRejected(@PathVariable("id") Long id,
                                                      @PathVariable("eventId") Long eventId,
                                                      @PathVariable("reqId") Long reqId) {
        return null;
    }
}