package ru.practicum.explore_with_me.events.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
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
@FieldDefaults(makeFinal = false, level = AccessLevel.PRIVATE)
public class Event {
    @Transient
    final String eventId = "event_id";
    @Transient
    final String eventCategoryId = "event_category_id";
    @Transient
    final String eventAnnotation = "event_annotation";
    @Transient
    final String eventCreated = "event_created";
    @Transient
    final String eventDescription = "event_description";
    @Transient
    final String eventDate = "event_date";
    @Transient
    final String eventInitiatorId = "event_initiator_id";
    @Transient
    final String eventLocationId = "event_location_id";
    @Transient
    final String eventPaid = "event_paid";
    @Transient
    final String eventParticipantLimit = "event_participant_limit";
    @Transient
    final String eventPublished = "event_published";
    @Transient
    final String eventRequestModeration = "event_request_moderation";
    @Transient
    final String eventState = "event_state";
    @Transient
    final String eventTitle = "event_title";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = eventId)
    Long id;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = eventCategoryId)
    Category category;
    @Column(name = eventAnnotation)
    String annotation;
    @Column(name = eventCreated)
    LocalDateTime createdOn;
    @Column(name = eventDescription)
    String description;
    @Column(name = eventDate)
    LocalDateTime date;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = eventInitiatorId)
    User initiator;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = eventLocationId)
    Location location;
    @Column(name = eventPaid)
    Boolean paid;
    @Column(name = eventParticipantLimit)
    Integer participantLimit;
    @Column(name = eventPublished)
    LocalDateTime publishedOn;
    @Column(name = eventRequestModeration)
    Boolean requestModeration;
    @Column(name = eventState)
    @Enumerated(EnumType.STRING)
    EventStateEnum state;
    @Column(name = eventTitle)
    String title;
}
