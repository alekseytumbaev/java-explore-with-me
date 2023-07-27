package ru.practicum.ewm.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.controller.AdminApi;
import ru.practicum.ewm.model.dto.*;
import ru.practicum.ewm.model.parameter.EventParameter;
import ru.practicum.ewm.service.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AdminController implements AdminApi {

    private final UserService userService;
    private final CategoryService categoryService;
    private final EventService eventService;
    private final CompilationService compilationService;
    private final CommentService commentService;

    //---/users
    @Override
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        List<UserDto> users = userService.getUsers(ids, from, size);
        log.info("Found {} users, params: ids={}, from={}, size={}", users.size(), ids, from, size);
        return users;
    }

    @Override
    public UserDto registerUser(NewUserRequest newUserRequest) {
        UserDto userDto = userService.registerUser(newUserRequest);
        log.info("New user registered: {}", userDto);
        return userDto;
    }

    @Override
    public void delete(Long userId) {
        userService.delete(userId);
        log.info("User with id={} deleted", userId);
    }
    //---

    //---/categories
    @Override
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        CategoryDto categoryDto = categoryService.addCategory(newCategoryDto);
        log.info("New category added: {}", categoryDto);
        return categoryDto;
    }

    @Override
    public void deleteCategory(Long catId) {
        categoryService.deleteCategory(catId);
        log.info("Category with id={} deleted", catId);
    }

    @Override
    public CategoryDto updateCategory(Long catId, CategoryDto categoryDto) {
        categoryDto.setId(catId);
        CategoryDto updatedCategoryDto = categoryService.updateCategory(categoryDto);
        log.info("Category with id={} updated: {}", catId, updatedCategoryDto);
        return updatedCategoryDto;
    }
    //---

    //---/events
    @Override
    public List<EventFullDto> getEventsAdmin(List<Long> users, List<EventState> states, List<Long> categories,
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
        return events;
    }

    @Override
    public EventFullDto updateEventAdmin(Long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        EventFullDto eventFullDto = eventService.updateEventAdmin(eventId, updateEventAdminRequest);
        log.info("Event with id={} updated: {}", eventId, eventFullDto);
        return eventFullDto;
    }
    //---

    //---/compilations
    @Override
    public void deleteCompilation(Long compId) {
        compilationService.deleteCompilation(compId);
        log.info("Compilation with id={} deleted", compId);
    }

    @Override
    public CompilationDto saveCompilation(NewCompilationDto newCompilationDto) {
        CompilationDto compilationDto = compilationService.saveCompilation(newCompilationDto);
        log.info("New compilation saved: {}", compilationDto);
        return compilationDto;
    }

    @Override
    public CompilationDto updateCompilation(Long compId, UpdateCompilationRequest updateCompilationRequest) {
        CompilationDto compilationDto = compilationService.updateCompilation(compId, updateCompilationRequest);
        log.info("Compilation with id={} updated: {}", compId, compilationDto);
        return compilationDto;
    }
    //---

    //---/comments

    @Override
    public void deleteCommentAdmin(Long commentId) {
        commentService.deleteCommentAdmin(commentId);
        log.info("Comment with id={} deleted", commentId);
    }
}
