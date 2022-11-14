package ru.practicum.explore_with_me.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore_with_me.users.model.User;


public interface UserRepository extends JpaRepository<User,Long>  {
}
