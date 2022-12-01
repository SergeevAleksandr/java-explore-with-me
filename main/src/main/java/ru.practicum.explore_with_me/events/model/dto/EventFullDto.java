package ru.practicum.explore_with_me.events.model.dto;

import lombok.*;
import ru.practicum.explore_with_me.categories.model.dto.CategoryDto;
import ru.practicum.explore_with_me.locations.Location;
import ru.practicum.explore_with_me.users.model.dto.UserShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto {
    private String annotation;
    private CategoryDto category;
    private Long confirmedRequests;
    private String createdOn;
    private String description;
    private String eventDate;
    @NotNull
    private Long id;
    private UserShortDto initiator;
    private Location location;
    private Boolean paid;
    private Integer participantLimit;
    private String publishedOn;
    private Boolean requestModeration;
    private String state;
    @NotBlank
    private String title;
    private Long views;
}
