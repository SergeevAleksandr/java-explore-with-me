package ru.practicum.explore_with_me.categories.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explore_with_me.categories.model.Category;


public interface CategoryRepository extends JpaRepository<Category,Long>  {
}
