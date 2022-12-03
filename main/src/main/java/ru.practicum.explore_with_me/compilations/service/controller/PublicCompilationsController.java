package ru.practicum.explore_with_me.compilations.service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.compilations.model.dto.CompilationDto;
import ru.practicum.explore_with_me.compilations.service.CompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(path = "/compilations")
@RequiredArgsConstructor
public class PublicCompilationsController {
    private final CompilationService compilationService;

    @GetMapping()
    public List<CompilationDto> get(@RequestParam(name = "pinned", defaultValue = "false") Boolean pinned,
                                    @PositiveOrZero @RequestParam(name = "from", defaultValue = "0") Integer from,
                                    @Positive @RequestParam(name = "size", defaultValue = "10") Integer size) {
        int page = from / size;
        final PageRequest pageRequest = PageRequest.of(page, size);
        log.info("Получние подборок событий");
        return compilationService.get(pinned, pageRequest);
    }

    @GetMapping(value = "/{compId}")
    public CompilationDto getById(@PathVariable("compId") Long id) {
        log.info("Получение подюорки событий по его id - {}",id);
        return compilationService.getById(id);
    }
}