package ru.practicum.explore_with_me.events.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explore_with_me.categories.model.Category;
import ru.practicum.explore_with_me.events.model.enums.EventStateEnum;
import ru.practicum.explore_with_me.locations.Location;
import ru.practicum.explore_with_me.users.model.User;


import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "event_category_id")
    private Category category;
    @Column(name = "event_annotation")
    private String annotation;
    @Column(name = "event_created")
    private LocalDateTime createdOn;
    @Column(name = "event_description")
    private String description;
    @Column(name = "event_date")
    private LocalDateTime date;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "event_initiator_id")
    private User initiator;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "event_location_id")
    private Location location;
    @Column(name = "event_paid")
    private Boolean paid;
    @Column(name = "event_participant_limit")
    private Integer participantLimit;
    @Column(name = "event_published")
    private LocalDateTime publishedOn;
    @Column(name = "event_request_moderation")
    private Boolean requestModeration;
    @Column(name = "event_state")
    @Enumerated(EnumType.STRING)
    private EventStateEnum state;
    @Column(name = "event_title")
    private String title;
}
