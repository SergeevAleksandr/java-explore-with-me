package ru.practicum.explore_with_me.events.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.explore_with_me.categories.model.dto.CategoryDto;
import ru.practicum.explore_with_me.locations.Location;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewEventShortDto {
    private String annotation;
    private CategoryDto category;
    private String description;
    private String eventDate;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;
    @NotBlank
    private String title;
}
