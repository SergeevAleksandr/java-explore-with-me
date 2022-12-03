package ru.practicum.explore_with_me.events.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(makeFinal = false, level = AccessLevel.PRIVATE)
public class UpdateEventRequest {
    String annotation;
    Long category;
    String description;
    String eventDate;
    Long eventId;
    Boolean paid;
    Integer participantLimit;
    String title;
}