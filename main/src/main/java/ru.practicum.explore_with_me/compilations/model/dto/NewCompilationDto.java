package ru.practicum.explore_with_me.compilations.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto {
    private List<Long> events;
    @NotNull
    private Boolean pinned;
    @NotBlank
    private String title;
}
