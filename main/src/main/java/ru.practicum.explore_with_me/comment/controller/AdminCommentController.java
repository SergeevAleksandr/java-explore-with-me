package ru.practicum.explore_with_me.comment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.comment.model.dto.CommentFullDto;
import ru.practicum.explore_with_me.comment.model.dto.NewCommentDto;
import ru.practicum.explore_with_me.comment.service.CommentService;


@RestController
@Slf4j
@RequestMapping("/admin/events/{eventId}/comments")
@RequiredArgsConstructor
public class AdminCommentController {
    private final String comment = "/{commentId}";
    private final CommentService commentService;

    @PostMapping
    public CommentFullDto createComment(@PathVariable("eventId") Long eventId,
                                        @Validated @RequestBody NewCommentDto newComment) {
        log.debug("Пользователь - {} добавляет новый коммннтарий к событию- {}",eventId);
        return commentService.createAdminComment(eventId, newComment);
    }

    @DeleteMapping(comment)
    public void delete(@PathVariable("eventId") Long eventId,
                       @PathVariable("commentId") Long commentId) {
        log.debug("Удаляем любой коммент");
        commentService.deleteAdminComment(eventId, commentId);
    }
}