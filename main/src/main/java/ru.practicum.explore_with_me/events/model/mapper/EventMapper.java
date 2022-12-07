package ru.practicum.explore_with_me.events.model.mapper;

import ru.practicum.explore_with_me.categories.model.Category;
import ru.practicum.explore_with_me.categories.model.mapper.CategoryMapper;
import ru.practicum.explore_with_me.comment.model.dto.CommentFullDto;
import ru.practicum.explore_with_me.events.model.Event;
import ru.practicum.explore_with_me.events.model.dto.EventFullDto;
import ru.practicum.explore_with_me.events.model.dto.EventShortDto;
import ru.practicum.explore_with_me.events.model.dto.NewEventDto;
import ru.practicum.explore_with_me.events.model.enums.EventStateEnum;
import ru.practicum.explore_with_me.locations.Location;
import ru.practicum.explore_with_me.users.model.User;
import ru.practicum.explore_with_me.users.model.mapper.UserMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EventMapper {
    public static Event toEvent(NewEventDto event, Location location, Category category, User initiator) {
        return new Event(
                0L,
                category,
                event.getAnnotation(),
                LocalDateTime.now(),
                event.getDescription(),
                LocalDateTime.parse(event.getEventDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                initiator,
                location,
                event.getPaid(),
                event.getParticipantLimit(),
                null,
                event.getRequestModeration(),
                EventStateEnum.PENDING,
                event.getTitle()
        );
    }

    public static EventShortDto toEventShortDto(Event event, Long confirmedRequests, Long view,List<CommentFullDto> commentFullDtoList) {
        return new EventShortDto(
                event.getAnnotation(),
                CategoryMapper.toCategoryDto(event.getCategory()),
                confirmedRequests,
                event.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                event.getId(),
                UserMapper.toUserShortDto(event.getInitiator()),
                event.getPaid(),
                event.getTitle(),
                view,
                commentFullDtoList
        );
    }

    public static EventFullDto toEventFullDto(Event event, Long confirmRequests, Long views, List<CommentFullDto> commentFullDtoList) {
        return new EventFullDto(
                event.getAnnotation(),
                CategoryMapper.toCategoryDto(event.getCategory()),
                confirmRequests,
                event.getCreatedOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                event.getDescription(),
                event.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                event.getId(),
                UserMapper.toUserShortDto(event.getInitiator()),
                event.getLocation(),
                event.getPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn() != null ? event.getPublishedOn().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : null,
                event.getRequestModeration(),
                event.getState().toString(),
                event.getTitle(),
                views,
                commentFullDtoList
        );
    }
}
