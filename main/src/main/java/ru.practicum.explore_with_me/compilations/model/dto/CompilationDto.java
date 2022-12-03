package ru.practicum.explore_with_me.compilations.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore_with_me.events.model.dto.EventShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(makeFinal = false, level = AccessLevel.PRIVATE)
public class CompilationDto {
    @NotNull
    Long id;
    List<EventShortDto> events;
    @NotNull
    Boolean pinned;
    @NotBlank
    String title;
}
