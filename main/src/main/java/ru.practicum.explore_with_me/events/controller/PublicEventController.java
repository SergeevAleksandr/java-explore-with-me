package ru.practicum.explore_with_me.events.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.events.model.dto.EventFullDto;
import ru.practicum.explore_with_me.events.model.dto.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/events")
@RequiredArgsConstructor
public class PublicEventController {
    @GetMapping()
    public List<EventShortDto> getEvents(@RequestParam(name = "text") String text,
                                         @RequestParam(name = "categories") Long[] categories,
                                         @RequestParam(name = "paid") Boolean paid,
                                         @RequestParam(name = "rangeStart") String rangeStart,
                                         @RequestParam(name = "rangeEnd") String rangeEnd,
                                         @RequestParam(name = "onlyAvailable", defaultValue = "false") Boolean onlyAvailable,
                                         @RequestParam(name = "sort") String sort,
                                         @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                         @Positive @RequestParam(name = "size", defaultValue = "10") Integer size,
                                         HttpServletRequest request
                                         ) {
        return null;
    }
    @GetMapping(value = "/{id}")
    public EventFullDto getEventForId(@PathVariable("id")  long id,
                                      HttpServletRequest request){

        return null;
    }
}