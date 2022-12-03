package ru.practicum.explore_with_me.events.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.explore_with_me.events.model.Event;
import ru.practicum.explore_with_me.events.model.enums.EventStateEnum;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface EventRepository extends JpaRepository<Event,Long>  {
    @Query(value = "select e FROM Event e " +
            "WHERE ((COALESCE(:text,NULL) IS NULL OR LOWER(e.annotation) LIKE CONCAT('%',LOWER(:text),'%')) " +
            "OR (COALESCE(:text, NULL) IS NULL OR LOWER(e.description) LIKE CONCAT('%', lower(:text), '%'))) " +
            "AND (COALESCE(:categories, NULL) IS NULL OR e.category.id IN :categories) " +
            "AND (COALESCE(:paid, NULL) IS NULL OR e.paid = :paid) " +
            "AND e.state = 'PUBLISHED' AND e.date BETWEEN :rangeStart AND :rangeEnd")
    List<Event> get(@Param("text") String text,@Param("categories") List<Long> categories,@Param("paid") Boolean paid,
                    @Param("rangeStart") LocalDateTime rangeStart,@Param("rangeEnd") LocalDateTime rangeEnd,
                    Pageable page);

    List<Event> getEventsByInitiatorId(long id, Pageable page);

    @Query(value = "select e from Event e " +
            "where ((coalesce(:users, null) is null or e.initiator.id in :users)) " +
            "and (coalesce(:categories, null) is null or e.category.id in :categories) " +
            "and (coalesce(:states, null) is null or e.state in :states) " +
            "and e.date between :rangeStart AND :rangeEnd")
    List<Event> getAdminEvents(@Param("users")  List<Long> users, @Param("categories") List<Long> categories,
                               @Param("rangeStart") LocalDateTime rangeStart,
                               @Param("rangeEnd") LocalDateTime rangeEnd,
                               @Param("states") List<EventStateEnum> states, Pageable page);

    Collection<Object> getEventsByCategoryId(Long categoryId);
}
