package ru.practicum.explore_with_me.events.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventRequest {
    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private Long eventId;
    private Boolean paid;
    private Integer participantLimit;
    private String title;
}