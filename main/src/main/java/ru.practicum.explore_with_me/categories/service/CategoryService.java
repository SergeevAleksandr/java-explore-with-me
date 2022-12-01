package ru.practicum.explore_with_me.categories.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.explore_with_me.categories.model.Category;
import ru.practicum.explore_with_me.categories.model.dto.CategoryDto;
import ru.practicum.explore_with_me.categories.model.mapper.CategoryMapper;
import ru.practicum.explore_with_me.categories.repository.CategoryRepository;
import ru.practicum.explore_with_me.events.repository.EventRepository;
import ru.practicum.explore_with_me.exception.ConflictException;
import ru.practicum.explore_with_me.exception.ObjectNotFoundException;
import ru.practicum.explore_with_me.exception.ValidationException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    public final EventRepository eventsRepository;

    public CategoryDto update(CategoryDto category) {
        Category updatedCategory = categoryRepository.findById(category.getId()).orElseThrow(() ->
                new ObjectNotFoundException("Категория с таким id не найдена"));
        List<Category> allCategory = categoryRepository.findAll();
                allCategory.remove(category.getId());
        if (allCategory.stream().map(Category::getName).collect(Collectors.toList()).contains(category.getName())) {
            throw new ConflictException("Имя занято");
        }
        updatedCategory.setName(category.getName());
        return CategoryMapper.toCategoryDto(categoryRepository.save(updatedCategory));
    }

    public CategoryDto create(CategoryDto category) {
        if (categoryRepository.findAll().stream().map(Category::getName).collect(Collectors.toList()).contains(category.getName())) {
            throw new ConflictException("Имя занято");
        }
        Category newCategory = new Category(0L, category.getName());
        return CategoryMapper.toCategoryDto(categoryRepository.save(newCategory));
    }

    public void delete(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ObjectNotFoundException("Категория с таким id не найдена"));
        if (!eventsRepository.getEventsByCategoryId(categoryId).isEmpty()) {
            throw new ValidationException("Есть события с данной категорией, удаление невозможно");
        }
        categoryRepository.delete(category);
    }

    public List<CategoryDto> get(PageRequest pageRequest) {
        return categoryRepository.findAll(pageRequest).stream().map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    public CategoryDto getById(Long id) {
        return CategoryMapper.toCategoryDto(categoryRepository.findById(id).orElseThrow(() ->
                new ObjectNotFoundException("Категория с таким id не найдена!")));
    }
}
