package ru.practicum.explore_with_me.users.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.explore_with_me.users.model.User;

import java.util.List;


public interface UserRepository extends JpaRepository<User,Long>  {
    @Query(value = "SELECT u FROM User u " +
            "WHERE ((coalesce(:ids, NULL) IS NULL OR u.id IN :ids))")
    List<User> getUsers(List<Long> ids, Pageable pagerequest);
}
