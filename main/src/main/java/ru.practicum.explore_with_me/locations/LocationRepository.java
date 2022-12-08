package ru.practicum.explore_with_me.locations;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> getLocationByLatAndLon(Double lat, Double lon);
}