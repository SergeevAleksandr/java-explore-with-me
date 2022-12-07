package ru.practicum.explore_with_me.comment.model.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.explore_with_me.comment.model.Comment;
import ru.practicum.explore_with_me.comment.model.dto.CommentFullDto;
import ru.practicum.explore_with_me.comment.model.dto.NewCommentDto;
import ru.practicum.explore_with_me.events.model.Event;
import ru.practicum.explore_with_me.users.model.User;

import java.time.format.DateTimeFormatter;

@Component
public class CommentMapper {

    public static Comment toCommentEntity(NewCommentDto commentDto, User commentator, Event event) {
        Comment comment = new Comment();
        comment.setText(commentDto.getText());
        comment.setEvent(event);
        comment.setCommentator(commentator);
        comment.setByAdmin(commentDto.getByAdmin());
        comment.setChanged(commentDto.getChanged());

        return comment;
    }

    public static CommentFullDto toCommentFullDto(Comment comment) {
        return CommentFullDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .eventId(comment.getEvent().getId())
                .commentatorId(comment.getCommentator() == null ? null : comment.getCommentator().getId())
                .commentDate(comment.getCommentDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .byAdmin(comment.getByAdmin())
                .changed(comment.getChanged()).build();
    }
}
