package ru.practicum.explore_with_me.compilations.model.mapper;

import ru.practicum.explore_with_me.compilations.model.Compilation;
import ru.practicum.explore_with_me.compilations.model.dto.CompilationDto;
import ru.practicum.explore_with_me.events.model.dto.EventShortDto;

import java.util.List;

public class CompilationMapper {
    public static CompilationDto toCompilationDTO(Compilation compilation, List<EventShortDto> eventList) {
        return new CompilationDto(
                compilation.getId(),
                compilation.getTitle(),
                compilation.getPinned(),
                eventList
        );
    }
}
