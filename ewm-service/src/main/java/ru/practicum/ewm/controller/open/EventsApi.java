
package ru.practicum.ewm.controller.open;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.ewm.model.dto.EventFullDto;
import ru.practicum.ewm.model.dto.EventShortDto;
import ru.practicum.ewm.model.dto.EventSort;
import ru.practicum.ewm.stats.contant.Patterns;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Validated
@RequestMapping("/events")
public interface EventsApi {

    @GetMapping("/{id}")
    EventFullDto getEventPublic(
            @PathVariable("id") Long id
    );

    @GetMapping
    List<EventShortDto> getEventsPublic(
            @Valid @RequestParam(value = "text", required = false) @Size(min = 1, max = 7000) String text,
            @Valid @RequestParam(value = "categories", required = false) List<Long> categories,
            @Valid @RequestParam(value = "paid", required = false) Boolean paid,
            @Valid @RequestParam(value = "rangeStart", required = false) @DateTimeFormat(pattern = Patterns.dateTimePattern) LocalDateTime rangeStart,
            @Valid @RequestParam(value = "rangeEnd", required = false) @DateTimeFormat(pattern = Patterns.dateTimePattern) LocalDateTime rangeEnd,
            @Valid @RequestParam(value = "onlyAvailable", required = false, defaultValue = "false") Boolean onlyAvailable,
            @Valid @RequestParam(value = "sort", required = false) EventSort sort,
            @Valid @RequestParam(value = "from", required = false, defaultValue = "0") @PositiveOrZero Integer from,
            @Valid @RequestParam(value = "size", required = false, defaultValue = "10") @Positive Integer size
    );

}
