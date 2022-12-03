package ru.practicum.explore_with_me.events.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.events.model.EventParameters;
import ru.practicum.explore_with_me.events.model.dto.EventFullDto;
import ru.practicum.explore_with_me.events.model.dto.EventShortDto;
import ru.practicum.explore_with_me.events.service.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/events")
@RequiredArgsConstructor
public class PublicEventController {

    private final EventService eventService;

    @GetMapping()
    public List<EventShortDto> get(@RequestParam(name = "text",required = false) String text,
                                         @RequestParam(name = "categories",required = false) Long[] categories,
                                         @RequestParam(name = "paid",required = false) Boolean paid,
                                         @RequestParam(name = "rangeStart",required = false) String rangeStart,
                                         @RequestParam(name = "rangeEnd",required = false) String rangeEnd,
                                         @RequestParam(name = "onlyAvailable", defaultValue = "false") Boolean onlyAvailable,
                                         @RequestParam(name = "sort",required = false) String sort,
                                         @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                         @Positive @RequestParam(name = "size", defaultValue = "10") Integer size,
                                         HttpServletRequest request
                                         ) {
        EventParameters parameters = new EventParameters(
                text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
        log.info("Получаем события с возможностью фильтрации");
        return eventService.get(parameters, request);
    }

    @GetMapping(value = "/{id}")
    public EventFullDto getEventForId(@PathVariable("id")  long id, HttpServletRequest request) {
        log.info("Получаем подробную информацию об опубликованном событии по его идентификатору - {}",id);
        return eventService.getById(id, request);
    }
}