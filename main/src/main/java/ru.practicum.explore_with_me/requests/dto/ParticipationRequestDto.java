package ru.practicum.explore_with_me.requests.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequestDto {
    private String created;
    private Integer event;
    private Integer id;
    private Integer requester;
    private String status;
}