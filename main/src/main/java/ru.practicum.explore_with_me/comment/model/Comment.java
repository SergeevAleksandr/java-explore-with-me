package ru.practicum.explore_with_me.comment.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explore_with_me.events.model.Event;
import ru.practicum.explore_with_me.users.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Transient
    final String commentId = "comment_id";
    @Transient
    final String commentText = "comment_text";
    @Transient
    final String eventId = "event_id";
    @Transient
    final String commentatorId = "commentator_id";
    @Transient
    final String commentDateString = "comment_date";
    @Transient
    final String byAdminString = "byAdmin";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = commentId)
    private Long id;
    @Column(name = commentText)
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = eventId)
    private Event event;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = commentatorId)
    private User commentator;
    @JoinColumn(name = commentDateString)
    private LocalDateTime commentDate = LocalDateTime.now();
    @JoinColumn(name = byAdminString)
    private Boolean byAdmin;
    private Boolean changed;
}
