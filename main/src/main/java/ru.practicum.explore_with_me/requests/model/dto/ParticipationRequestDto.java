package ru.practicum.explore_with_me.requests.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(makeFinal = false, level = AccessLevel.PRIVATE)
public class ParticipationRequestDto {
    String created;
    Long event;
    Long id;
    Long requester;
    String status;
}