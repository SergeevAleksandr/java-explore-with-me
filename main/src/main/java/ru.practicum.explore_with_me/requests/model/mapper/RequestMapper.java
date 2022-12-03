package ru.practicum.explore_with_me.requests.model.mapper;

import ru.practicum.explore_with_me.requests.model.Request;
import ru.practicum.explore_with_me.requests.model.dto.ParticipationRequestDto;

import java.time.format.DateTimeFormatter;

public class RequestMapper {
    public static ParticipationRequestDto toParticipationRequestDto(Request request) {
        return new ParticipationRequestDto(
                request.getCreated().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                request.getEvent().getId(),
                request.getId(),
                request.getRequester().getId(),
                request.getStatus().toString()
        );
    }
}
