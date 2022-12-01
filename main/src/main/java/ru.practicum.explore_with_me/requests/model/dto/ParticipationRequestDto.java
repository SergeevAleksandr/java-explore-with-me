package ru.practicum.explore_with_me.requests.model.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestDto {
    private String created;
    private Long event;
    private Long id;
    private Long requester;
    private String status;
}