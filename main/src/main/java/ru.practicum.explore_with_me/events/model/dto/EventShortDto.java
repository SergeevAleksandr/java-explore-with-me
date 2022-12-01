package ru.practicum.explore_with_me.events.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.explore_with_me.categories.model.dto.CategoryDto;
import ru.practicum.explore_with_me.users.model.dto.UserShortDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDto {
    private String annotation;
    private CategoryDto category;
    private Long confirmedRequests;
    private String eventDate;
    @NotNull
    private Long id;
    private UserShortDto initiator;
    private Boolean paid;
    @NotBlank
    private String title;
    private Long views;
}
