package ru.practicum.explore_with_me.compilations.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.explore_with_me.events.model.Event;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "compilations")
@FieldDefaults(makeFinal = false, level = AccessLevel.PRIVATE)
public class Compilation {
    @Transient
    final String compilationId = "compilation_id";
    @Transient
    final String compilationTitle = "compilation_title";
    @Transient
    final String compilationPinned = "compilation_pinned";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = compilationId)
    Long id;
    @Column(name = "compilationTitle")
    String title;
    @Column(name = "compilationPinned")
    Boolean pinned;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "compilations_events",
            joinColumns = {@JoinColumn(name = compilationId)},
            inverseJoinColumns = {@JoinColumn(name = "event_id")}
    )
    @ToString.Exclude
    List<Event> events = new ArrayList<>();
}
