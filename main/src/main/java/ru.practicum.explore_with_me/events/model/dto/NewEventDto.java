package ru.practicum.explore_with_me.events.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore_with_me.locations.LocationDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(makeFinal = false, level = AccessLevel.PRIVATE)
public class NewEventDto {
    @NotBlank
    String annotation;
    @NotNull
    Long category;
    @NotBlank
    String description;
    @NotBlank
    String eventDate;
    @NotNull
    LocationDto location;
    Boolean paid;
    Integer participantLimit;
    Boolean requestModeration;
    @NotBlank
    String title;
}