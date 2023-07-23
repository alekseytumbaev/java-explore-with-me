package ru.practicum.ewm.controller.open;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewm.model.dto.CompilationDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Validated
@RequestMapping("/compilations")
public interface CompilationApi {

    @GetMapping("{compId}")
    CompilationDto getCompilation(
            @PathVariable("compId") Long compId
    );

    @GetMapping
    List<CompilationDto> getCompilations(
            @Valid @RequestParam(value = "pinned", required = false) Boolean pinned,
            @Valid @RequestParam(value = "from", required = false, defaultValue = "0") @PositiveOrZero Integer from,
            @Valid @RequestParam(value = "size", required = false, defaultValue = "10") @Positive Integer size
    );
}
