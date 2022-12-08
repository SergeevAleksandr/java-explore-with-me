package ru.practicum.explore_with_me.categories.model.mapper;

import ru.practicum.explore_with_me.categories.model.Category;
import ru.practicum.explore_with_me.categories.model.dto.CategoryDto;

public class CategoryMapper {
    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }
}
