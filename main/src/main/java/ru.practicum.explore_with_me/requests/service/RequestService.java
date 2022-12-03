package ru.practicum.explore_with_me.requests.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explore_with_me.events.model.Event;
import ru.practicum.explore_with_me.events.model.enums.EventStateEnum;
import ru.practicum.explore_with_me.events.repository.EventRepository;
import ru.practicum.explore_with_me.exception.ObjectNotFoundException;
import ru.practicum.explore_with_me.exception.ValidationException;
import ru.practicum.explore_with_me.requests.model.Request;
import ru.practicum.explore_with_me.requests.model.dto.ParticipationRequestDto;
import ru.practicum.explore_with_me.requests.model.enums.RequestStatusEnum;
import ru.practicum.explore_with_me.requests.model.mapper.RequestMapper;
import ru.practicum.explore_with_me.requests.repository.RequestRepository;
import ru.practicum.explore_with_me.users.model.User;
import ru.practicum.explore_with_me.users.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public List<ParticipationRequestDto> get(Long userId) {
        User user = checkUser(userId);
        return requestRepository.getRequestsByRequesterId(user.getId()).stream()
                .map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList());
    }

    public ParticipationRequestDto create(Long userId, Long eventId) {
        User user = checkUser(userId);
        if (requestRepository.getRequestByRequesterIdAndEventId(userId, eventId).isPresent()) {
            throw new ValidationException("Повторный запрос");
        }
        Event event = checkEvent(eventId);
        if (event.getInitiator().getId().equals(eventId)) {
            throw new ValidationException("Нельзя отправить запрос на свое событие");
        }
        if (!event.getState().equals(EventStateEnum.PUBLISHED)) {
            throw new ValidationException("Нельзя отправить запрос на неопубликованное событие");
        }
        int confirmedRequests = requestRepository.getRequestsByEventIdAndStatus(eventId, RequestStatusEnum.CONFIRMED).size();
        int limit = event.getParticipantLimit();
        if (limit == confirmedRequests) {
            throw new ValidationException("Лимит запросов достигнут");
        }

        Request request = new Request(0L, RequestStatusEnum.PENDING, user, LocalDateTime.now(), event);
        if (!event.getRequestModeration()) {
            request.setStatus(RequestStatusEnum.CONFIRMED);
        }

        return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    public ParticipationRequestDto cancel(Long userId, Long requestId) {
        User user = checkUser(userId);
        Request request = checkRequest(requestId);
        if (!request.getRequester().getId().equals(user.getId())) {
            throw new ValidationException("Нельзя отменить чужой запрос!");
        }
        request.setStatus(RequestStatusEnum.CANCELED);
        return RequestMapper.toParticipationRequestDto(requestRepository.save(request));
    }

    public User checkUser(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Пользователь с таким id не найден"));
    }

    public Event checkEvent(Long id) {
        return eventRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Событие с таким id не найдено!"));
    }

    public Request checkRequest(Long id) {
        return requestRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Запрос с таким id не найден"));
    }
}
