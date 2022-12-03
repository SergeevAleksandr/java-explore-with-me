package ru.practicum.explore_with_me.compilations.service.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.compilations.model.dto.CompilationDto;
import ru.practicum.explore_with_me.compilations.model.dto.NewCompilationDto;
import ru.practicum.explore_with_me.compilations.service.CompilationService;

@RestController
@Slf4j
@RequestMapping(path = "/admin/compilations")
@RequiredArgsConstructor
public class AdminCompilationsController {
    private final CompilationService compilationService;

    @PostMapping
    public CompilationDto create(@RequestBody NewCompilationDto newCompilation) {
        log.info("Добавление новой подборки - {}", newCompilation.getTitle());
        return compilationService.create(newCompilation);
    }

    @DeleteMapping(value = "/{compId}")
    public void delete(@PathVariable("compId") Long compilationId) {
        log.info("Удаление подборки - {}", compilationId);
        compilationService.deleteById(compilationId);
    }

    @DeleteMapping(value = "/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@PathVariable("compId") Long compilationId,
                                           @PathVariable("eventId") Long eventId) {
        log.info("Удаление события - {} из подборки - {}", eventId, compilationId);
        compilationService.deleteEventFromCompilation(compilationId, eventId);
    }

    @PatchMapping(value = "/{compId}/events/{eventId}")
    public void addEventToCompilation(@PathVariable("compId") Long compilationId,
                                      @PathVariable("eventId") Long eventId) {
        log.info("Добавление события - {} в подборку - {}", eventId, compilationId);
        compilationService.addEventToCompilation(compilationId, eventId);
    }

    @DeleteMapping(value = "/{compId}/pin")
    public void deletePinCompilation(@PathVariable("compId") Long compilationId) {
        log.info("Открепить подборку - {} на главной странице", compilationId);
        compilationService.setPinCompilation(compilationId, Boolean.FALSE);
    }

    @PatchMapping(value = "/{compId}/pin")
    public void addPinCompilation(@PathVariable("compId") Long compilationId) {
        log.info("Закрепить подборку - {} на главной странице", compilationId);
        compilationService.setPinCompilation(compilationId, Boolean.TRUE);
    }
}
