package ru.practicum.explore_with_me.events.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.events.model.dto.EventFullDto;
import ru.practicum.explore_with_me.events.model.dto.EventShortDto;
import ru.practicum.explore_with_me.events.model.dto.NewEventDto;
import ru.practicum.explore_with_me.events.model.dto.UpdateEventRequest;
import ru.practicum.explore_with_me.events.service.EventService;
import ru.practicum.explore_with_me.requests.model.dto.ParticipationRequestDto;

import javax.persistence.Transient;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/users/{id}/events")
@RequiredArgsConstructor
public class PrivateEventController {
    final String eventIdString = "/{eventId}";
    private final EventService eventService;

    @GetMapping()
    public List<EventShortDto> getAllUserEvents(@PathVariable("id") Long id,
                                              @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                              @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        int page = from / size;
        final PageRequest pageRequest = PageRequest.of(page, size);
        log.info("Получаем события добавленные текущим пользователем - {}",id);
        return eventService.getUserEvents(id, pageRequest);
    }

    @PatchMapping()
    public EventFullDto updateEvent(@PathVariable("id") Long id,
                                    @RequestBody UpdateEventRequest updateRequest) {
        log.info("Изменяем события добавленные текущим пользователем - {}",id);
        return eventService.updateEvent(id, updateRequest);
    }

    @PostMapping()
    public EventFullDto createEvent(@PathVariable("id") Long id,
                                    @RequestBody NewEventDto newEvent) {
        log.info("Добавляем новое событие - {} пользователем - {}", newEvent.getTitle(),id);
        return eventService.createEvent(id, newEvent);
    }

    @GetMapping(value = eventIdString)
    public EventFullDto getUserEventForId(@PathVariable("id") Long id,
                                       @PathVariable("eventId") Long eventId) {
        log.info("Получаем полную информацию об событии - {} добавленном пользователем - {}",eventId,id);
        return eventService.getUsersEventById(id, eventId);
    }

    @PatchMapping(value = eventIdString)
    public EventFullDto setCancelEvent(@PathVariable("id") Long id,
                                         @PathVariable("eventId") Long eventId) {
        log.info("Отмена события - {} добавленного пользователем {}",eventId,id);
        return eventService.setCancelledState(id, eventId);
    }

    @GetMapping(value = eventIdString + "/requests")
    public List<ParticipationRequestDto> getRequestsInUserEvent(@PathVariable("id") Long id,
                                                                @PathVariable("eventId") Long eventId) {
        log.info("Получаем информацию о запросах на участие по событию - {} пользователя {}", eventId,id);
        return eventService.getUsersRequestByEvent(id, eventId);
    }

    @PatchMapping(value = eventIdString + "/requests/{reqId}/confirm")
    public ParticipationRequestDto setRequestConfirmed(@PathVariable("id") Long id,
                                                       @PathVariable("eventId") Long eventId,
                                                       @PathVariable("reqId") Long reqId) {
        log.info("Подтверждаем чужой заявки - {} на участие в событии - {}", reqId, eventId);
        return eventService.setRequestConfirmed(id, eventId, reqId);
    }

    @PatchMapping(value = eventIdString + "/requests/{reqId}/reject")
    public ParticipationRequestDto setRequestRejected(@PathVariable("id") Long id,
                                                      @PathVariable("eventId") Long eventId,
                                                      @PathVariable("reqId") Long reqId) {
        log.info("Отклоняем чужую заявку на участие - {} в событии пользователя - {}", reqId, eventId);
        return eventService.setRequestRejected(id, eventId, reqId);
    }
}