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
    final String commentId = "comments_id";
    @Transient
    final String commentText = "comments_text";
    @Transient
    final String eventString = "comments_event_id";
    @Transient
    final String commentatorId = "comments_commentator_id";
    @Transient
    final String commentDateString = "comments_date";
    @Transient
    final String byAdminString = "comments_by_admin";
    @Transient
    final String commentsChanged = "comments_Changed";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = commentId)
    private Long id;
    @Column(name = commentText)
    private String text;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = eventString)
    private Event event;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = commentatorId)
    private User commentator;
    @Column(name = commentDateString)
    private LocalDateTime commentDate = LocalDateTime.now();
    @Column(name = byAdminString)
    private Boolean byAdmin;
    @Column(name = commentsChanged)
    private Boolean changed;
}
