package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.error.exception.CategoryNotFoundException;
import ru.practicum.ewm.mapper.CategoryMapper;
import ru.practicum.ewm.model.dto.CategoryDto;
import ru.practicum.ewm.model.dto.NewCategoryDto;
import ru.practicum.ewm.model.entity.Category;
import ru.practicum.ewm.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Service
@Transactional
@RequiredArgsConstructor
public class  CategoryService {

    private final CategoryRepository categoryRepo;

    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        Category category = categoryRepo.save(CategoryMapper.toEntity(newCategoryDto));
        return CategoryMapper.toDto(category);
    }

    public void deleteCategory(Long catId) {
        if (!categoryRepo.existsById(catId)) {
            throw new CategoryNotFoundException(format("Cannot delete category with id=%s, because it's not found", catId));
        }
        categoryRepo.deleteById(catId);
    }

    public CategoryDto updateCategory(CategoryDto categoryDto) {
        Optional<Category> categoryOpt = categoryRepo.findById(categoryDto.getId());
        if (categoryOpt.isEmpty()) {
            throw new CategoryNotFoundException(
                    format("Cannot update category with id=%d, because it's not found", categoryDto.getId()));
        }
        Category category = categoryOpt.get();
        category.setName(categoryDto.getName());
        return CategoryMapper.toDto(categoryRepo.save(category));
    }

    public List<CategoryDto> getCategories(Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        Page<Category> categories = categoryRepo.findAll(pageRequest);
        return categories.map(CategoryMapper::toDto).toList();
    }

    public CategoryDto getCategory(Long catId) {
         Category category = categoryRepo.findById(catId).orElseThrow(() ->
                 new CategoryNotFoundException(format("Cannot get category with id=%d, because it's not found", catId))
         );
        return CategoryMapper.toDto(category);
    }

    public Category getEntityById(Long catId) {
        return categoryRepo.findById(catId).orElseThrow(() ->
                new CategoryNotFoundException(format("Cannot get category with id=%d, because it's not found", catId)));
    }
}
