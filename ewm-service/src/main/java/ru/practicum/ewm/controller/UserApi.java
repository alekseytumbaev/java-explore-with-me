package ru.practicum.ewm.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.model.dto.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RequestMapping("/users")
@Validated
public interface UserApi {

    @PostMapping("/{userId}/events")
    @ResponseStatus(CREATED)
    EventFullDto addEvent(
            @PathVariable("userId") Long userId,
            @Valid @RequestBody NewEventDto newEventDto
    );

    @PostMapping("/{userId}/requests")
    @ResponseStatus(CREATED)
    ParticipationRequestDto addParticipationRequest(
            @PathVariable("userId") Long userId,
            @RequestParam("eventId") Long eventId
    );

    @PatchMapping("/{userId}/requests/{requestId}/cancel")
    ParticipationRequestDto cancelRequest(
            @PathVariable("userId") Long userId,
            @PathVariable("requestId") Long requestId
    );

    @PatchMapping("/{userId}/events/{eventId}/requests")
    EventRequestStatusUpdateResult changeRequestStatus(
            @PathVariable("userId") Long userId,
            @PathVariable("eventId") Long eventId,
            @Valid @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest
    );

    @GetMapping("/{userId}/events/{eventId}")
    EventFullDto getEvent(
            @PathVariable("userId") Long userId,
            @PathVariable("eventId") Long eventId
    );

    @GetMapping("/{userId}/events/{eventId}/requests")
    List<ParticipationRequestDto> getEventParticipants(
            @PathVariable("userId") Long userId,
            @PathVariable("eventId") Long eventId
    );


    @GetMapping("/{userId}/events")
    List<EventShortDto> getEvents(
            @PathVariable("userId") Long userId,
            @Valid @RequestParam(value = "from", required = false, defaultValue = "0") @PositiveOrZero Integer from,
            @Valid @RequestParam(value = "size", required = false, defaultValue = "10") @Positive Integer size
    );

    @GetMapping("/{userId}/requests")
    List<ParticipationRequestDto> getUserRequests(
            @PathVariable("userId") Long userId
    );

    @PatchMapping("/{userId}/events/{eventId}")
    EventFullDto updateEvent(
            @PathVariable("userId") Long userId,
            @PathVariable("eventId") Long eventId,
            @Valid @RequestBody UpdateEventUserRequest updateEventUserRequest
    );

}
