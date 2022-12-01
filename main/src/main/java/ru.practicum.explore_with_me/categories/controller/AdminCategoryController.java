package ru.practicum.explore_with_me.categories.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explore_with_me.categories.model.dto.CategoryDto;
import ru.practicum.explore_with_me.categories.service.CategoryService;

@RestController
@Slf4j
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryService categoryService;

    @PatchMapping()
    public CategoryDto update(@RequestBody CategoryDto category) {
        log.info("Изменение категории - {}", category.getId());
        return categoryService.update(category);
    }

    @PostMapping
    public CategoryDto create(@RequestBody CategoryDto category) {
        log.info("Добавление новой категории - {}", category.getName());
        return categoryService.create(category);
    }

    @DeleteMapping(value = "/{catId}")
    public void delete(@PathVariable("catId") Long categoryId) {
        log.info("Удаление категории - {}", categoryId);
        categoryService.delete(categoryId);
    }
}
