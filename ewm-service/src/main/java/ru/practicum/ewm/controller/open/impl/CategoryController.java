package ru.practicum.ewm.controller.open.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.practicum.ewm.controller.open.CategoriesApi;
import ru.practicum.ewm.model.dto.CategoryDto;
import ru.practicum.ewm.service.CategoryService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CategoryController implements CategoriesApi {
    private final CategoryService categoryService;

    @Override
    public ResponseEntity<List<CategoryDto>> getCategories(Integer from, Integer size) {
        List<CategoryDto> categories = categoryService.getCategories(from, size);
        log.info("Found {} categories, params: from={}, size={}", categories.size(), from, size);
        return ResponseEntity.ok(categories);
    }

    @Override
    public ResponseEntity<CategoryDto> getCategory(Long catId) {
        CategoryDto categoryDto = categoryService.getCategory(catId);
        log.info("Found category with id: {}", catId);
        return ResponseEntity.ok(categoryDto);
    }
}
