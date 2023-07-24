package ru.practicum.ewm.controller.open;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewm.model.dto.CategoryDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@RequestMapping("/categories")
public interface CategoryApi {

    @GetMapping
    List<CategoryDto> getCategories(
            @Valid @RequestParam(value = "from", required = false, defaultValue = "0") @PositiveOrZero Integer from,
            @Valid @RequestParam(value = "size", required = false, defaultValue = "10") @Positive Integer size
    );

    @GetMapping("{catId}")
    CategoryDto getCategory(
            @PathVariable("catId") Long catId
    );
}
