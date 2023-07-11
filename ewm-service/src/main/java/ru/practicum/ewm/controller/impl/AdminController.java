package ru.practicum.ewm.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.practicum.ewm.controller.AdminApi;
import ru.practicum.ewm.model.dto.*;
import ru.practicum.ewm.model.parameter.EventParameter;
import ru.practicum.ewm.service.CategoryService;
import ru.practicum.ewm.service.CompilationService;
import ru.practicum.ewm.service.EventService;
import ru.practicum.ewm.service.UserService;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AdminController implements AdminApi {

    private final UserService userService;
    private final CategoryService categoryService;
    private final EventService eventService;
    private final CompilationService compilationService;

    //---/users
    @Override
    public ResponseEntity<List<UserDto>> getUsers(List<Long> ids, Integer from, Integer size) {
        List<UserDto> users = userService.getUsers(ids, from, size);
        log.info("Found {} users, params: ids={}, from={}, size={}", users.size(), ids, from, size);
        return ResponseEntity.ok(users);
    }

    @Override
    public ResponseEntity<UserDto> registerUser(NewUserRequest newUserRequest) {
        UserDto userDto = userService.registerUser(newUserRequest);
        log.info("New user registered: {}", userDto);
        return new ResponseEntity<>(userDto, CREATED);
    }

    @Override
    public ResponseEntity<Void> delete(Long userId) {
        userService.delete(userId);
        log.info("User with id={} deleted", userId);
        return ResponseEntity.noContent().build();
    }
    //---

    //---/categories
    @Override
    public ResponseEntity<CategoryDto> addCategory(NewCategoryDto newCategoryDto) {
        CategoryDto categoryDto = categoryService.addCategory(newCategoryDto);
        log.info("New category added: {}", categoryDto);
        return new ResponseEntity<>(categoryDto, CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteCategory(Long catId) {
        categoryService.deleteCategory(catId);
        log.info("Category with id={} deleted", catId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<CategoryDto> updateCategory(Long catId, CategoryDto categoryDto) {
        categoryDto.setId(catId);
        CategoryDto updatedCategoryDto = categoryService.updateCategory(categoryDto);
        log.info("Category with id={} updated: {}", catId, updatedCategoryDto);
        return ResponseEntity.ok(updatedCategoryDto);
    }
    //---

    //---/events
    @Override
    public ResponseEntity<List<EventFullDto>> getEventsAdmin(List<Long> users, List<EventFullDto.StateEnum> states, List<Long> categories,
                                                             LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                             Integer from, Integer size) {
        EventParameter eventParameter = EventParameter.builder()
                .initiatorIds(users)
                .states(states)
                .categoryIds(categories)
                .notBefore(rangeStart)
                .notAfter(rangeEnd)
                .build();

        List<EventFullDto> events = eventService.getAllEventsAdmin(eventParameter, from, size);
        log.info("Found {} events, params: eventParameter={}, from={}, size={}",
                events.size(), eventParameter, from, size);
        return ResponseEntity.ok(events);
    }

    @Override
    public ResponseEntity<EventFullDto> updateEventAdmin(Long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        EventFullDto eventFullDto = eventService.updateEventAdmin(eventId, updateEventAdminRequest);
        log.info("Event with id={} updated: {}", eventId, eventFullDto);
        return ResponseEntity.ok(eventFullDto);
    }
    //---

    //---/compilations
    @Override
    public ResponseEntity<Void> deleteCompilation(Long compId) {
        compilationService.deleteCompilation(compId);
        log.info("Compilation with id={} deleted", compId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<CompilationDto> saveCompilation(NewCompilationDto newCompilationDto) {
        CompilationDto compilationDto = compilationService.saveCompilation(newCompilationDto);
        log.info("New compilation saved: {}", compilationDto);
        return new ResponseEntity<>(compilationDto, CREATED);
    }

    @Override
    public ResponseEntity<CompilationDto> updateCompilation(Long compId, UpdateCompilationRequest updateCompilationRequest) {
        CompilationDto compilationDto = compilationService.updateCompilation(compId, updateCompilationRequest);
        log.info("Compilation with id={} updated: {}", compId, compilationDto);
        return ResponseEntity.ok(compilationDto);
    }
    //---
}
