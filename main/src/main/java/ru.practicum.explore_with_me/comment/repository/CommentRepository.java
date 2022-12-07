package ru.practicum.explore_with_me.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore_with_me.comment.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
