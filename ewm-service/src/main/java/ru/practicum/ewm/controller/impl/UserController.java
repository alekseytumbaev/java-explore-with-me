package ru.practicum.ewm.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import ru.practicum.ewm.controller.UsersApi;
import ru.practicum.ewm.model.dto.*;
import ru.practicum.ewm.service.EventService;
import ru.practicum.ewm.service.ParticipationRequestService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController implements UsersApi {

    private final EventService eventService;
    private final ParticipationRequestService requestService;

    //-- /events
    @Override
    public ResponseEntity<EventFullDto> addEvent(Long userId, NewEventDto newEventDto) {
        EventFullDto eventFullDto = eventService.addEvent(userId, newEventDto);
        log.info("New event added: {}", eventFullDto);
        return new ResponseEntity<>(eventFullDto, CREATED);
    }

    @Override
    public ResponseEntity<EventFullDto> getEvent(Long userId, Long eventId) {
        EventFullDto eventFullDto = eventService.getEventByInitiatorId(userId, eventId);
        log.info("Event found: {}", eventFullDto);
        return ResponseEntity.ok(eventFullDto);
    }


    @Override
    public ResponseEntity<List<EventShortDto>> getEvents(Long userId, Integer from, Integer size) {
        List<EventShortDto> eventDtos = eventService.getAllEventsByInitiatorId(userId, from, size);
        log.info("Found {} events, params: userId={}, from={}, size={}", eventDtos.size(), userId, from, size);
        return ResponseEntity.ok(eventDtos);
    }

    @Override
    public ResponseEntity<EventFullDto> updateEvent(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest) {
        EventFullDto eventDto = eventService.updateEvent(userId, eventId, updateEventUserRequest);
        log.info("Event updated: {}", eventDto);
        return ResponseEntity.ok(eventDto);
    }

    @Override
    public ResponseEntity<List<ParticipationRequestDto>> getEventParticipants(Long userId, Long eventId) {
        List<ParticipationRequestDto> participationRequestDtos = requestService.getByEventInitiatorIdAndEventId(userId, eventId);
        log.info("Found {} participation requests, params: userId={}, eventId={}", participationRequestDtos.size(), userId, eventId);
        return ResponseEntity.ok(participationRequestDtos);
    }

    @Override
    public ResponseEntity<EventRequestStatusUpdateResult> changeRequestStatus(Long userId, Long eventId,
                                                                              EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        EventRequestStatusUpdateResult result = requestService.changeRequestStatus(
                userId, eventId, eventRequestStatusUpdateRequest);
        log.info("Request status changed: {}", result);
        return ResponseEntity.ok(result);
    }
    //--

    //-- /requests
    @Override
    public ResponseEntity<ParticipationRequestDto> addParticipationRequest(Long userId, Long eventId) {
        ParticipationRequestDto request = requestService.addParticipationRequest(userId, eventId);
        log.info("Request added: {}", request);
        return new ResponseEntity<>(request, CREATED);
    }

    @Override
    public ResponseEntity<ParticipationRequestDto> cancelRequest(Long userId, Long requestId) {
        ParticipationRequestDto requestDto = requestService.cancelRequest(userId, requestId);
        log.info("Request canceled: {}", requestDto);
        return ResponseEntity.ok(requestDto);
    }

    @Override
    public ResponseEntity<List<ParticipationRequestDto>> getUserRequests(Long userId) {
        List<ParticipationRequestDto> participationRequestDtos = requestService.getByRequesterId(userId);
        log.info("Found {} requests, params: userId={}", participationRequestDtos.size(), userId);
        return ResponseEntity.ok(participationRequestDtos);
    }
    //--
}
