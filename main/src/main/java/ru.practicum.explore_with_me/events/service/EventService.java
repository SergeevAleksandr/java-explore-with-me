package ru.practicum.explore_with_me.events.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explore_with_me.categories.model.Category;
import ru.practicum.explore_with_me.categories.repository.CategoryRepository;
import ru.practicum.explore_with_me.comment.model.Comment;
import ru.practicum.explore_with_me.comment.model.dto.CommentFullDto;
import ru.practicum.explore_with_me.comment.model.mapper.CommentMapper;
import ru.practicum.explore_with_me.comment.repository.CommentRepository;
import ru.practicum.explore_with_me.events.model.AdminEventParameters;
import ru.practicum.explore_with_me.events.model.Event;
import ru.practicum.explore_with_me.events.model.EventParameters;
import ru.practicum.explore_with_me.events.model.dto.*;
import ru.practicum.explore_with_me.locations.Location;
import ru.practicum.explore_with_me.events.model.enums.EventSortEnum;
import ru.practicum.explore_with_me.events.model.enums.EventStateEnum;
import ru.practicum.explore_with_me.events.model.mapper.EventMapper;
import ru.practicum.explore_with_me.events.repository.EventRepository;
import ru.practicum.explore_with_me.exception.ObjectNotFoundException;
import ru.practicum.explore_with_me.exception.ValidationException;
import ru.practicum.explore_with_me.locations.LocationRepository;
import ru.practicum.explore_with_me.requests.model.Request;
import ru.practicum.explore_with_me.requests.model.dto.ParticipationRequestDto;
import ru.practicum.explore_with_me.requests.model.enums.RequestStatusEnum;
import ru.practicum.explore_with_me.requests.model.mapper.RequestMapper;
import ru.practicum.explore_with_me.requests.repository.RequestRepository;
import ru.practicum.explore_with_me.stats.StatsService;
import ru.practicum.explore_with_me.users.model.User;
import ru.practicum.explore_with_me.users.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final StatsService statsService;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final CommentRepository commentRepository;


    public List<EventShortDto> get(EventParameters parameters, HttpServletRequest request) {
        List<Event> eventList = eventRepository.get(parameters.getText(), parameters.getCategories(), parameters.getPaid(),
                parameters.getRangeStart(), parameters.getRangeEnd(), parameters.getPageRequest());
        statsService.hit(request.getRequestURI(), request.getRemoteAddr());
        Map<Long, Long> viewsStat = statsService.getViews(eventList, false);
        if (parameters.getSort() != null) {
            if (parameters.getSort().equals(EventSortEnum.EVENT_DATE)) {
                return eventList.stream().map(e -> {
                    List<CommentFullDto> commentFullDtoList = commentRepository.findAllByEventId(e.getId())
                            .stream().sorted(Comparator.comparing(Comment::getCommentDate))
                            .map(CommentMapper::toCommentFullDto).collect(Collectors.toList());
                    return EventMapper.toEventShortDto(e, requestRepository.getConfirmedRequests(e.getId()),
                            viewsStat.getOrDefault(e.getId(), 0L),commentFullDtoList);
                }).collect(Collectors.toList());
            } else {
                return eventList.stream().map(e -> {
                            List<CommentFullDto> commentFullDtoList = commentRepository.findAllByEventId(e.getId())
                                    .stream().sorted(Comparator.comparing(Comment::getCommentDate))
                                    .map(CommentMapper::toCommentFullDto).collect(Collectors.toList());
                            return EventMapper.toEventShortDto(e, requestRepository.getConfirmedRequests(e.getId()),
                                    viewsStat.getOrDefault(e.getId(), 0L),commentFullDtoList);
                        }).sorted(Comparator.comparingLong(EventShortDto::getViews))
                        .collect(Collectors.toList());
            }
        }
        return eventList.stream().map(e -> {
            List<CommentFullDto> commentFullDtoList = commentRepository.findAllByEventId(e.getId())
                    .stream().sorted(Comparator.comparing(Comment::getCommentDate))
                    .map(CommentMapper::toCommentFullDto).collect(Collectors.toList());
            return EventMapper.toEventShortDto(e, requestRepository.getConfirmedRequests(e.getId()),
                    viewsStat.getOrDefault(e.getId(), 0L),commentFullDtoList);
        }).collect(Collectors.toList());
    }

    public EventFullDto getById(long eventId, HttpServletRequest request) {
        Event event = checkEvent(eventId);
        if (event.getState() != EventStateEnum.PUBLISHED) {
            throw new ValidationException("Событие не опубликовано");
        }
        statsService.hit(request.getRequestURI(), request.getRemoteAddr());
        Long viewsStat = statsService.getView(event, false);
        List<CommentFullDto> commentFullDtoList = commentRepository.findAllByEventId(event.getId())
                .stream().sorted(Comparator.comparing(Comment::getCommentDate))
                .map(CommentMapper::toCommentFullDto).collect(Collectors.toList());
        return EventMapper.toEventFullDto(event, requestRepository.getConfirmedRequests(event.getId()), viewsStat,commentFullDtoList);
    }

    public List<EventShortDto> getUserEvents(long userId, Pageable page) {
        User user = checkUser(userId);
        List<Event> eventList = eventRepository.getEventsByInitiatorId(user.getId(), page);
        Map<Long, Long> viewsStat = statsService.getViews(eventList,false);
        return eventList.stream().map(e -> EventMapper.toEventShortDto(e, requestRepository.getConfirmedRequests(e.getId()),
                viewsStat.getOrDefault(e.getId(), 0L),new ArrayList<>())).collect(Collectors.toList());
    }

    public EventFullDto updateEvent(Long userId, UpdateEventRequest updateRequest) {
        User user = checkUser(userId);
        Event event = checkEvent(updateRequest.getEventId());
        if (!Objects.equals(event.getInitiator().getId(), user.getId())) {
            throw new ValidationException("Нельзя обновлять не свои события");
        }
        if (event.getState() == EventStateEnum.PUBLISHED) {
            throw new ValidationException("Нельзя обновлять опубликованное событие");
        }
        if (event.getDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ValidationException("Нельзя обновлять событие до начала которого осталось менее 2-х часов");
        }
        if (event.getState() == EventStateEnum.CANCELED) {
            event.setState(EventStateEnum.PENDING);
        }
        if (updateRequest.getPaid() != null) {
            event.setPaid(updateRequest.getPaid());
        }
        if (updateRequest.getEventDate() != null) {
            event.setDate(LocalDateTime.parse(updateRequest.getEventDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        if (updateRequest.getAnnotation() != null) {
            event.setAnnotation(updateRequest.getAnnotation());
        }
        if (updateRequest.getCategory() != null) {
            Category category = checkCategory(updateRequest.getCategory());
            event.setCategory(category);
        }
        if (updateRequest.getTitle() != null) event.setTitle(updateRequest.getTitle());
        if (updateRequest.getDescription() != null) event.setDescription(updateRequest.getDescription());
        if (updateRequest.getParticipantLimit() != null) event.setParticipantLimit(updateRequest.getParticipantLimit());
        eventRepository.save(event);
        Long viewsStat = statsService.getView(event, false);
        return EventMapper.toEventFullDto(event, requestRepository.getConfirmedRequests(event.getId()), viewsStat,new ArrayList<>());
    }

    public EventFullDto createEvent(Long id, NewEventDto newEvent) {
        User user = checkUser(id);
        Category category = checkCategory(newEvent.getCategory());
        LocalDateTime eventDate = LocalDateTime.parse(newEvent.getEventDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        if (eventDate.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ValidationException("Нельзя Добавлять событие до начала которого осталось менее 2-х часов");
        }
        Double lat = newEvent.getLocation().getLat();
        Double lon = newEvent.getLocation().getLon();
        Optional<Location> locationOptional = locationRepository.getLocationByLatAndLon(lat, lon);
        Location location;
        location = locationOptional.orElseGet(() -> locationRepository.save(new Location(0L, lat, lon)));
        return EventMapper.toEventFullDto(eventRepository.save(EventMapper.toEvent(newEvent, location, category, user)), 0L, 0L,new ArrayList<>());
    }

    public EventFullDto getUsersEventById(Long userId, Long eventId) {
        User user = checkUser(userId);
        Event event = checkEvent(eventId);
        checkUserEvent(event,user);
        Long viewsStat = statsService.getView(event, false);
        return EventMapper.toEventFullDto(event, requestRepository.getConfirmedRequests(event.getId()), viewsStat,new ArrayList<>());
    }

    public EventFullDto setCancelledState(Long userId, Long eventId) {
        User user = checkUser(userId);
        Event event = checkEvent(eventId);
        checkUserEvent(event,user);
        if (!event.getState().equals(EventStateEnum.PENDING)) {
            throw new ValidationException("Можно отклонить событие только со статусом PENDING ");
        }
        event.setState(EventStateEnum.CANCELED);

        Long viewsStat = statsService.getView(event, false);
        return EventMapper.toEventFullDto(event, requestRepository.getConfirmedRequests(event.getId()), viewsStat,new ArrayList<>());
    }

    public List<ParticipationRequestDto> getUsersRequestByEvent(Long userId, Long eventId) {
        User user = checkUser(userId);
        Event event = checkEvent(eventId);
        if (!event.getInitiator().getId().equals(user.getId())) {
            throw new ValidationException("Пользователь не является создателем события!");
        }
        return requestRepository.getAllByEventId(eventId).stream()
                .map(RequestMapper::toParticipationRequestDto).collect(Collectors.toList());
    }

    public ParticipationRequestDto setRequestConfirmed(Long userId, Long eventId, Long reqId) {
        User user = checkUser(userId);
        Event event = checkEvent(eventId);
        checkUserEvent(event,user);
        Request request = checkRequest(reqId);
        int confirmedRequests = requestRepository.getRequestsByEventIdAndStatus(eventId, RequestStatusEnum.CONFIRMED).size();
        int limit = event.getParticipantLimit();
        if (limit == 0 || !event.getRequestModeration()) {
            throw new ValidationException("Для этого события не требуется подтверждение");
        }
        if (limit == confirmedRequests) {
            throw new ValidationException("Лимит подтверждений исчерпан");
        }
        request.setStatus(RequestStatusEnum.CONFIRMED);
        requestRepository.save(request);
        if (limit - confirmedRequests == 1) {
            List<Request> requestList = requestRepository.getRequestsByEventIdAndNotStatus(eventId);
            for (Request requestRej : requestList) {
                requestRej.setStatus(RequestStatusEnum.REJECTED);
                requestRepository.save(requestRej);
            }
        }
        return RequestMapper.toParticipationRequestDto(request);
    }

    public ParticipationRequestDto setRequestRejected(Long userId, Long eventId, Long reqId) {
        User user = checkUser(userId);
        Event event = checkEvent(eventId);
        checkUserEvent(event,user);
        Request request = checkRequest(reqId);
        request.setStatus(RequestStatusEnum.REJECTED);
        requestRepository.save(request);
        return RequestMapper.toParticipationRequestDto(request);
    }

    public List<EventFullDto> getAdminEvents(AdminEventParameters params) {
        List<Event> eventList = eventRepository.getAdminEvents(params.getUsers(), params.getCategories(),
                params.getRangeStart(), params.getRangeEnd(), params.getStates(), params.getPageRequest());
        Map<Long, Long> viewsStat = statsService.getViews(eventList, false);
        return eventList.stream().map(e -> {
                List<CommentFullDto> commentFullDtoList = commentRepository.findAllByEventId(e.getId())
                        .stream().sorted(Comparator.comparing(Comment::getCommentDate))
                        .map(CommentMapper::toCommentFullDto).collect(Collectors.toList());
                return EventMapper.toEventFullDto(e, requestRepository.getConfirmedRequests(e.getId()),
                    viewsStat.getOrDefault(e.getId(), 0L),commentFullDtoList);
        }).collect(Collectors.toList());
    }

    public EventFullDto updateAdminEvent(Long eventId, AdminUpdateEventRequest updateRequest) {
        Event event = checkEvent(eventId);
        if (updateRequest.getPaid() != null) {
            event.setPaid(updateRequest.getPaid());
        }
        if (updateRequest.getEventDate() != null) {
            event.setDate(LocalDateTime.parse(updateRequest.getEventDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        if (updateRequest.getAnnotation() != null) {
            event.setAnnotation(updateRequest.getAnnotation());
        }
        if (updateRequest.getCategory() != null) {
            Category category = checkCategory(updateRequest.getCategory());
            event.setCategory(category);
        }
        if (updateRequest.getTitle() != null) {
            event.setTitle(updateRequest.getTitle());
        }
        if (updateRequest.getDescription() != null) {
            event.setDescription(updateRequest.getDescription());
        }
        if (updateRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(updateRequest.getParticipantLimit());
        }
        if (updateRequest.getRequestModeration() != null) {
            event.setRequestModeration(updateRequest.getRequestModeration());
        }
        if (updateRequest.getLocation() != null) {
            Double lat = updateRequest.getLocation().getLat();
            Double lon = updateRequest.getLocation().getLon();
            Optional<Location> locationOptional = locationRepository.getLocationByLatAndLon(lat, lon);
            Location location = locationOptional.orElseGet(() -> locationRepository.save(new Location(0L, lat, lon)));
            event.setLocation(location);
        }
        eventRepository.save(event);
        Long viewsStat = statsService.getView(event, false);
        return EventMapper.toEventFullDto(event, requestRepository.getConfirmedRequests(event.getId()), viewsStat,new ArrayList<>());

    }

    public EventFullDto setPublished(Long eventId) {
        Event event = checkEvent(eventId);
        event.setPublishedOn(LocalDateTime.now());
        if (event.getPublishedOn().isAfter(event.getDate().plusHours(1))) {
            throw new ValidationException("Дата начала события должна быть не ранее чем за час от даты публикации.");
        }
        if (!event.getState().equals(EventStateEnum.PENDING)) {
            throw new ValidationException("Невозможно изменить состояние");
        }
        event.setState(EventStateEnum.PUBLISHED);
        eventRepository.save(event);
        Long viewsStat = statsService.getView(event, false);
        return EventMapper.toEventFullDto(event, requestRepository.getConfirmedRequests(event.getId()), viewsStat,new ArrayList<>());
    }

    public EventFullDto setRejected(Long eventId) {
        Event event = checkEvent(eventId);
        if (event.getState().equals(EventStateEnum.PUBLISHED)) {
            throw new ValidationException("Невозможно изменить состояние!");
        }
        event.setState(EventStateEnum.CANCELED);
        eventRepository.save(event);
        Long viewsStat = statsService.getView(event, false);
        return EventMapper.toEventFullDto(event, requestRepository.getConfirmedRequests(event.getId()), viewsStat,new ArrayList<>());
    }

    public User checkUser(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Пользователь с таким id не найден"));
    }

    public Event checkEvent(Long id) {
        return eventRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Событие с таким id не найдено!"));
    }

    public Category checkCategory(Long id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Категория с таким id не найдена"));
    }

    public Request checkRequest(Long id) {
        return requestRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Запрос с таким id не найден"));
    }

    public void checkUserEvent(Event event,User user) {
        if (!Objects.equals(event.getInitiator().getId(), user.getId())) {
            throw new ValidationException("Нет доступа к чужому событию!");
        }
    }
}
