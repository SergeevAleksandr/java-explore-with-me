package ru.practicum.explore_with_me.locations;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "locations")
@FieldDefaults(makeFinal = false, level = AccessLevel.PRIVATE)
public class Location {
    @Transient
    final String locationId = "location_id";
    @Transient
    final String latString = "lat";
    @Transient
    final String lonString = "lon";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = locationId)
    Long id;
    @Column(name = latString)
    Double lat;
    @Column(name = lonString)
    Double lon;
}
