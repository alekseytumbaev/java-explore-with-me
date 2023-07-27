package ru.practicum.ewm.controller.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.controller.UserApi;
import ru.practicum.ewm.model.dto.*;
import ru.practicum.ewm.service.CommentService;
import ru.practicum.ewm.service.EventService;
import ru.practicum.ewm.service.ParticipationRequestService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController implements UserApi {

    private final EventService eventService;
    private final ParticipationRequestService requestService;
    private final CommentService commentService;

    //-- /events
    @Override
    public EventFullDto addEvent(Long userId, NewEventDto newEventDto) {
        EventFullDto eventFullDto = eventService.addEvent(userId, newEventDto);
        log.info("New event added: {}", eventFullDto);
        return eventFullDto;
    }

    @Override
    public EventFullDto getEvent(Long userId, Long eventId) {
        EventFullDto eventFullDto = eventService.getEventByInitiatorId(userId, eventId);
        log.info("Event found: {}", eventFullDto);
        return eventFullDto;
    }


    @Override
    public List<EventShortDto> getEvents(Long userId, Integer from, Integer size) {
        List<EventShortDto> eventDtos = eventService.getAllEventsByInitiatorId(userId, from, size);
        log.info("Found {} events, params: userId={}, from={}, size={}", eventDtos.size(), userId, from, size);
        return eventDtos;
    }

    @Override
    public EventFullDto updateEvent(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest) {
        EventFullDto eventDto = eventService.updateEvent(userId, eventId, updateEventUserRequest);
        log.info("Event updated: {}", eventDto);
        return eventDto;
    }

    @Override
    public List<ParticipationRequestDto> getEventParticipants(Long userId, Long eventId) {
        List<ParticipationRequestDto> participationRequestDtos = requestService.getByEventInitiatorIdAndEventId(userId, eventId);
        log.info("Found {} participation requests, params: userId={}, eventId={}", participationRequestDtos.size(), userId, eventId);
        return participationRequestDtos;
    }

    @Override
    public EventRequestStatusUpdateResult changeRequestStatus(Long userId, Long eventId,
                                                              EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        EventRequestStatusUpdateResult result = requestService.changeRequestStatus(
                userId, eventId, eventRequestStatusUpdateRequest);
        log.info("Request status changed: {}", result);
        return result;
    }
    //--

    //-- /requests
    @Override
    public ParticipationRequestDto addParticipationRequest(Long userId, Long eventId) {
        ParticipationRequestDto request = requestService.addParticipationRequest(userId, eventId);
        log.info("Request added: {}", request);
        return request;
    }

    @Override
    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        ParticipationRequestDto requestDto = requestService.cancelRequest(userId, requestId);
        log.info("Request canceled: {}", requestDto);
        return requestDto;
    }

    @Override
    public List<ParticipationRequestDto> getUserRequests(Long userId) {
        List<ParticipationRequestDto> participationRequestDtos = requestService.getByRequesterId(userId);
        log.info("Found {} requests, params: userId={}", participationRequestDtos.size(), userId);
        return participationRequestDtos;
    }
    //--

    //-- /comments

    @Override
    public CommentDto addComment(Long userId, Long eventId, CommentDto commentDto) {
        CommentDto comment = commentService.addComment(userId, eventId, commentDto);
        log.info("Comment added: {}", comment);
        return comment;
    }

    @Override
    public List<CommentDto> getCommentsByUserId(Long userId) {
        List<CommentDto> commentDtos = commentService.getCommentsByUserId(userId);
        log.info("Found {} comments, params: userId={}", commentDtos.size(), userId);
        return commentDtos;
    }

    @Override
    public CommentDto updateComment(Long userId, Long commentId, CommentDto commentDto) {
        CommentDto comment = commentService.updateComment(userId, commentId, commentDto);
        log.info("Comment updated: {}", comment);
        return comment;
    }

    @Override
    public void deleteComment(Long userId, Long commentId) {
        commentService.deleteComment(userId, commentId);
        log.info("Comment deleted: {}", commentId);
    }
}
