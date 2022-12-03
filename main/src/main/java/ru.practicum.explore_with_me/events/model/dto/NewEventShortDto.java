package ru.practicum.explore_with_me.events.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore_with_me.categories.model.dto.CategoryDto;
import ru.practicum.explore_with_me.locations.Location;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(makeFinal = false, level = AccessLevel.PRIVATE)
public class NewEventShortDto {
    String annotation;
    CategoryDto category;
    String description;
    String eventDate;
    Location location;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    @NotBlank
    String title;
}
