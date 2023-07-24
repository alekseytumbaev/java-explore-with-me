package ru.practicum.ewm.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.model.dto.CategoryDto;
import ru.practicum.ewm.model.dto.NewCategoryDto;
import ru.practicum.ewm.model.entity.Category;

@UtilityClass
public class CategoryMapper {
    public Category toEntity(NewCategoryDto categoryDto) {
        return new Category(
                null,
                categoryDto.getName()
        );
    }

    public CategoryDto toDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }
}
