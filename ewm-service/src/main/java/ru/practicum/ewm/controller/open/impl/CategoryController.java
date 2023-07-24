package ru.practicum.ewm.controller.open.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.controller.open.CategoryApi;
import ru.practicum.ewm.model.dto.CategoryDto;
import ru.practicum.ewm.service.CategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CategoryController implements CategoryApi {
    private final CategoryService categoryService;

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        List<CategoryDto> categories = categoryService.getCategories(from, size);
        log.info("Found {} categories, params: from={}, size={}", categories.size(), from, size);
        return categories;
    }

    @Override
    public CategoryDto getCategory(Long catId) {
        CategoryDto categoryDto = categoryService.getCategory(catId);
        log.info("Found category with id: {}", catId);
        return categoryDto;
    }
}
