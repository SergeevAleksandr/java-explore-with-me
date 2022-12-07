package ru.practicum.explore_with_me.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explore_with_me.comment.model.Comment;
import ru.practicum.explore_with_me.comment.model.dto.CommentFullDto;
import ru.practicum.explore_with_me.comment.model.dto.NewCommentDto;
import ru.practicum.explore_with_me.comment.model.dto.UpdateCommentDto;
import ru.practicum.explore_with_me.comment.model.mapper.CommentMapper;
import ru.practicum.explore_with_me.comment.repository.CommentRepository;
import ru.practicum.explore_with_me.events.model.Event;
import ru.practicum.explore_with_me.events.repository.EventRepository;
import ru.practicum.explore_with_me.exception.ObjectNotFoundException;

import ru.practicum.explore_with_me.exception.ValidationException;
import ru.practicum.explore_with_me.users.model.User;
import ru.practicum.explore_with_me.users.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public  CommentFullDto createAdminComment(Long eventId, NewCommentDto newComment) {
        Event event = checkEvent(eventId);
        Comment comment = CommentMapper.toCommentEntity(newComment,null, event);
        return CommentMapper.toCommentFullDto(commentRepository.save(comment));
    }

    public void deleteAdminComment(Long eventId, Long commentId) {
        checkEvent(eventId);
        Comment comment = checkComment(commentId);
        checkСommentEvent(eventId,comment);
        commentRepository.delete(comment);
    }

    public CommentFullDto createPrivateComment(Long userId, Long eventId, NewCommentDto newComment) {
        Event event = checkEvent(eventId);
        User user = checkUser(userId);
        Comment comment = CommentMapper.toCommentEntity(newComment,user, event);
        return CommentMapper.toCommentFullDto(commentRepository.save(comment));
    }

    public CommentFullDto updatePrivateComment(Long userId, Long eventId, Long commentId, UpdateCommentDto updateComment) {
        Event event = checkEvent(eventId);
        User user = checkUser(userId);
        Comment comment = checkComment(commentId);
        checkСommentEvent(eventId,comment);
        checkUserСomment(userId,comment);
        comment.setChanged(Boolean.TRUE);
        return CommentMapper.toCommentFullDto(commentRepository.save(comment));
    }

    public void deletePrivateComment(Long userId, Long eventId, Long commentId) {
        checkEvent(eventId);
        Comment comment = checkComment(commentId);
        checkСommentEvent(eventId,comment);
        checkUserСomment(userId,comment);
        commentRepository.delete(comment);
    }

    public Event checkEvent(Long id) {
       return eventRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Такого события не существует нельзя добавить комментарий"));
    }

    public Comment checkComment(Long id) {
       return commentRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Такого комментарий не существует нельзя удалить комментарий"));
    }

    public User checkUser(Long id) {
       return userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Такого позьзователя не существует нельзя добавить комментарий"));
    }

    public void checkUserСomment(Long userId,Comment comment) {
        if (!userId.equals(comment.getCommentator().getId())) {
            throw new ValidationException("Нельзя редактировать/удалять чужой комментарий");
        }
    }

    public void checkСommentEvent(Long eventId,Comment comment) {
        if (!eventId.equals(comment.getEvent().getId())) {
            throw new ValidationException("Что то пошло не так, комментарий не пренадлежит указанному событию");
        }
    }
}
