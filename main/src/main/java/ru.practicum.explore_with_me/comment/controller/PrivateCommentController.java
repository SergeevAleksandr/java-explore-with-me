package ru.practicum.explore_with_me.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.comment.model.dto.CommentFullDto;
import ru.practicum.explore_with_me.comment.model.dto.NewCommentDto;
import ru.practicum.explore_with_me.comment.model.dto.UpdateCommentDto;
import ru.practicum.explore_with_me.comment.service.CommentService;

@RestController
@Slf4j
@RequestMapping("/users/{userId}/events/{eventId}/comments")
@RequiredArgsConstructor
public class PrivateCommentController {
    final String comment = "/{commentId}";
    private final CommentService commentService;

    @PostMapping
    public CommentFullDto createComment(@PathVariable("userId") Long userId,
                                        @PathVariable("eventId") Long eventId,
                                        @RequestBody NewCommentDto newComment) {
        log.info("Пользователь - {} добавляет новый коммннтарий к событию- {}",userId,eventId);
        return commentService.createPrivateComment(userId,eventId, newComment);
    }

    @PatchMapping(comment)
    public CommentFullDto createComment(@PathVariable("userId") Long userId,
                                          @PathVariable("eventId") Long eventId,
                                          @PathVariable("commentId") Long commentId,
                                          @RequestBody UpdateCommentDto updateComment) {
        log.info("Изменяем коментарий {} добавленный текущим пользователем - {}",commentId,userId);
        return commentService.updatePrivateComment(userId,eventId,commentId,updateComment);
    }

    @DeleteMapping(comment)
    public void delete(@PathVariable("userId") Long userId,
                       @PathVariable("eventId") Long eventId,
                       @PathVariable("commentId") Long commentId) {
        log.debug("Удаляем свой комментарий - {}",commentId);
        commentService.deletePrivateComment(userId, eventId, commentId);
    }
}