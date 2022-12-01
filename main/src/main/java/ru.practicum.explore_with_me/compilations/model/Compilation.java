package ru.practicum.explore_with_me.compilations.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.practicum.explore_with_me.events.model.Event;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "compilations")
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "compilation_id")
    private Long id;
    @Column(name = "compilation_title")
    private String title;
    @Column(name = "compilation_pinned")
    private Boolean pinned;
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "compilations_events",
            joinColumns = {@JoinColumn(name = "compilation_id")},
            inverseJoinColumns = {@JoinColumn(name = "event_id")}
    )
    @ToString.Exclude
    private List<Event> events = new ArrayList<>();
}
