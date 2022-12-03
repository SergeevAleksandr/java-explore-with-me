package ru.practicum.explore_with_me.requests.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore_with_me.events.model.Event;
import ru.practicum.explore_with_me.requests.model.enums.RequestStatusEnum;
import ru.practicum.explore_with_me.users.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "requests")
@FieldDefaults(makeFinal = false, level = AccessLevel.PRIVATE)
public class Request {
    @Transient
    final String requestId = "request_id";
    @Transient
    final String requestStatus = "request_status";
    @Transient
    final String requestRequesterId = "request_requester_id";
    @Transient
    final String requestCreated = "request_created";
    @Transient
    final String requestEventId = "request_event_id";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = requestId)
    Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = requestStatus)
    RequestStatusEnum status;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = requestRequesterId)
    User requester;
    @Column(name = requestCreated)
    LocalDateTime created;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = requestEventId)
    Event event;
}

