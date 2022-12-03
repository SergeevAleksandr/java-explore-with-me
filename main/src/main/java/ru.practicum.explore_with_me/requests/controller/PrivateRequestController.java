package ru.practicum.explore_with_me.requests.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.requests.model.dto.ParticipationRequestDto;
import ru.practicum.explore_with_me.requests.service.RequestService;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/users/{userId}/requests")
@RequiredArgsConstructor
public class PrivateRequestController {
    private final RequestService requestService;

    @GetMapping()
    public List<ParticipationRequestDto> get(@PathVariable("userId") Long userId) {
        log.info("Получаем информацию о заявках текущего пользователя - {} на учатстие в чужих событиях",userId);
        return requestService.get(userId);
    }

    @PostMapping()
    public ParticipationRequestDto create(@PathVariable("userId") Long userId,
                                          @RequestParam("eventId") Long eventId) {
        log.info("Добавляем запрос от текущего пользователя - {} на участие в событии id - {}", userId, eventId);
        return requestService.create(userId, eventId);
    }

    @PatchMapping(value = "/{requestId}/cancel")
    public ParticipationRequestDto cancel(@PathVariable("userId") Long userId,
                                          @PathVariable("requestId") Long requestId) {
        log.info("Отменяем свой запрос на участие в событие");
        return requestService.cancel(userId, requestId);
    }
}
