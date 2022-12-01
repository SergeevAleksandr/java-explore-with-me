package ru.practicum.explore_with_me.compilations.model.dto;

import lombok.*;
import ru.practicum.explore_with_me.events.model.dto.EventShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {
    @NotNull
    private Long id;
    private List<EventShortDto> events;
    @NotNull
    private Boolean pinned;
    @NotBlank
    private String title;
}
