package ru.practicum.ewm.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.dto.*;
import ru.practicum.ewm.stats.contant.Patterns;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RequestMapping("/admin")
public interface AdminApi {

    @PostMapping("/categories")
    @ResponseStatus(CREATED)
    CategoryDto addCategory(
            @Valid @RequestBody NewCategoryDto newCategoryDto
    );

    @DeleteMapping("/users/{userId}")
    @ResponseStatus(NO_CONTENT)
    void delete(
            @PathVariable("userId") Long userId
    );

    @DeleteMapping("/categories/{catId}")
    @ResponseStatus(NO_CONTENT)
    void deleteCategory(
            @PathVariable("catId") Long catId
    );

    @DeleteMapping("/compilations/{compId}")
    @ResponseStatus(NO_CONTENT)
    void deleteCompilation(
            @PathVariable("compId") Long compId
    );

    @GetMapping("/events")
    List<EventFullDto> getEventsAdmin(
            @Valid @RequestParam(value = "users", required = false) List<Long> users,
            @Valid @RequestParam(value = "states", required = false) List<EventState> states,
            @Valid @RequestParam(value = "categories", required = false) List<Long> categories,
            @Valid @RequestParam(value = "rangeStart", required = false) @DateTimeFormat(pattern = Patterns.dateTimePattern) LocalDateTime rangeStart,
            @Valid @RequestParam(value = "rangeEnd", required = false) @DateTimeFormat(pattern = Patterns.dateTimePattern) LocalDateTime rangeEnd,
            @Valid @RequestParam(value = "from", required = false, defaultValue = "0") @PositiveOrZero Integer from,
            @Valid @RequestParam(value = "size", required = false, defaultValue = "10") @Positive Integer size
    );

    @GetMapping("/users")
    List<UserDto> getUsers(
            @Valid @RequestParam(value = "ids", required = false) List<Long> ids,
            @Valid @RequestParam(value = "from", required = false, defaultValue = "0") @PositiveOrZero Integer from,
            @Valid @RequestParam(value = "size", required = false, defaultValue = "10") @Positive Integer size
    );

    @PostMapping("/users")
    @ResponseStatus(CREATED)
    UserDto registerUser(
            @Valid @RequestBody NewUserRequest newUserRequest
    );

    @PostMapping("/compilations")
    @ResponseStatus(CREATED)
    CompilationDto saveCompilation(
            @Valid @RequestBody NewCompilationDto newCompilationDto
    );

    @PatchMapping("/categories/{catId}")
    CategoryDto updateCategory(
            @PathVariable("catId") Long catId,
            @Valid @RequestBody CategoryDto categoryDto
    );

    @PatchMapping("/compilations/{compId}")
    CompilationDto updateCompilation(
            @PathVariable("compId") Long compId,
            @Valid @RequestBody UpdateCompilationRequest updateCompilationRequest
    );

    @PatchMapping("/events/{eventId}")
    EventFullDto updateEventAdmin(
            @PathVariable("eventId") Long eventId,
            @Valid @RequestBody UpdateEventAdminRequest updateEventAdminRequest
    );

    @DeleteMapping("/comments/{commentId}")
    @ResponseStatus(NO_CONTENT)
    void deleteCommentAdmin(@PathVariable("commentId") Long commentId);
}
