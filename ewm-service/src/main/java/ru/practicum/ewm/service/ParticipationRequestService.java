package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.error.exception.*;
import ru.practicum.ewm.mapper.ParticipationRequestMapper;
import ru.practicum.ewm.model.dto.*;
import ru.practicum.ewm.model.entity.Event;
import ru.practicum.ewm.model.entity.ParticipationRequest;
import ru.practicum.ewm.model.entity.User;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.ParticipationRequestRepository;
import ru.practicum.ewm.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;
import static ru.practicum.ewm.model.dto.ParticipationRequestStatus.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ParticipationRequestService {

    private final ParticipationRequestRepository requestRepo;
    private final EventRepository eventRepo;
    private final UserRepository userRepository;

    public List<ParticipationRequestDto> getByEventInitiatorIdAndEventId(Long initiatorId, Long eventId) {
        if (!eventRepo.existsById(eventId)) {
            throw new EventNotFoundException(format("Event with id=%d not found", eventId));
        }
        List<ParticipationRequest> requests = requestRepo.findAllByEvent_Initiator_IdAndEvent_Id(initiatorId, eventId);
        return requests.stream().map(ParticipationRequestMapper::toDto).collect(toList());
    }

    public EventRequestStatusUpdateResult changeRequestStatus(Long initiatorId, Long eventId,
                                                              EventRequestStatusUpdateRequest statusUpdateRequest) {
        Event event = eventRepo.findById(eventId).orElseThrow(() ->
                new EventNotFoundException(format("Cannot change status of requests for requester with id=%d " +
                        "for event with id=%d, because event not found", initiatorId, eventId)));

        List<ParticipationRequest> requests;
        List<Long> requestIds = statusUpdateRequest.getRequestIds();
        if (requestIds == null || requestIds.isEmpty()) {
            requests = requestRepo.findAllByEvent_Initiator_IdAndEvent_Id(initiatorId, eventId);
        } else {
            requests = requestRepo.findAllByEvent_Initiator_IdAndEvent_IdAndIdIn(initiatorId, eventId, requestIds);
        }

        //if event has no participant limit all requests was already confirmed automatically
        if (event.getParticipantLimit() == 0) {
            requests = requestRepo.findAllByEvent_Initiator_IdAndEvent_Id(initiatorId, eventId);
            List<ParticipationRequestDto> dtos = requests.stream().map(ParticipationRequestMapper::toDto).collect(toList());
            return new EventRequestStatusUpdateResult(dtos, List.of());
        }

        long confirmedRequestsCount = countEventConfirmedRequests(eventId);
        int participantLimit = event.getParticipantLimit();
        if (confirmedRequestsCount >= participantLimit) {
            throw new RequestUpdateRestrictedException(format("Cannot change status of requests for requester with id=%d " +
                    "for event with id=%d, because event has too many participants", initiatorId, eventId));
        }

        for (ParticipationRequest request : requests) {
            if (!request.getStatus().equals(PENDING)) {
                throw new RequestUpdateRestrictedException(format("Cannot change status of requests for requester with id=%d " +
                                "for event with id=%d, because status of request with id=%d is not PENDING",
                        initiatorId, request.getId(), eventId));
            }
        }

        List<ParticipationRequestDto> confirmedRequests = new ArrayList<>();
        List<ParticipationRequestDto> rejectedRequests = new ArrayList<>();

        for (ParticipationRequest request : requests) {
            if (statusUpdateRequest.getStatus().equals(CONFIRMED)) {
                request.setStatus(CONFIRMED);
                confirmedRequests.add(ParticipationRequestMapper.toDto(request));
                confirmedRequestsCount++;
            }
            if (statusUpdateRequest.getStatus().equals(REJECTED)) {
                request.setStatus(REJECTED);
                rejectedRequests.add(ParticipationRequestMapper.toDto(request));
            }

            //if participant limit is reached, all pending requests will be rejected
            if (confirmedRequestsCount >= participantLimit) {
                requestRepo.saveAll(requests);
                rejectedRequests = requests.stream()
                        .filter(r -> !r.getStatus().equals(CONFIRMED))
                        .map(ParticipationRequestMapper::toDto)
                        .collect(toList());
                requestRepo.updateAllStatusByEvent_IdAndPreviousStatus(eventId, PENDING, REJECTED);
                return new EventRequestStatusUpdateResult(confirmedRequests, rejectedRequests);
            }
        }

        requestRepo.saveAll(requests);
        return new EventRequestStatusUpdateResult(confirmedRequests, rejectedRequests);
    }

    public List<ParticipationRequestDto> getByRequesterId(Long userId) {
        List<ParticipationRequest> requests = requestRepo.findAllByRequester_Id(userId);
        return requests.stream().map(ParticipationRequestMapper::toDto).collect(toList());
    }

    public ParticipationRequestDto cancelRequest(Long userId, Long requestId) {
        ParticipationRequest request = requestRepo.findByIdAndRequester_Id(requestId, userId)
                .orElseThrow(() -> new RequestNotFoundException(format("Request with id=%d not found", requestId)));

        request.setStatus(CANCELED);
        return ParticipationRequestMapper.toDto(requestRepo.save(request));
    }

    public ParticipationRequestDto addParticipationRequest(Long userId, Long eventId) {
        Event event = eventRepo.findById(eventId).orElseThrow(() ->
                new EventNotFoundException(format("Event with id=%d not found", eventId)));

        if (requestRepo.existsByRequester_IdAndEvent_Id(userId, eventId)) {
            throw new RequestCreationRestrictedException(
                    format("Request with requester with id=%d and event with id=%d already exists", userId, eventId));
        }

        if (event.getInitiator().getId().equals(userId)) {
            throw new RequestCreationRestrictedException(
                    format("User with id=%d cannot add participation request for event with id=%d, " +
                            "because he is the initiator", userId, eventId));
        }

        if (!event.getState().equals(EventFullDto.StateEnum.PUBLISHED)) {
            throw new RequestCreationRestrictedException(format("Cannot create request for event with id=%d, " +
                    "because the event is not published", eventId));
        }

        long confirmedRequestsCount = countEventConfirmedRequests(eventId);
        ParticipationRequestStatus status = PENDING;
        if (event.getParticipantLimit() == 0 ||
                (!event.isRequestModeration() && confirmedRequestsCount < event.getParticipantLimit())) {
            status = CONFIRMED;
            confirmedRequestsCount++;
        }

        if (status == PENDING &&
                confirmedRequestsCount >= event.getParticipantLimit()) {
            throw new RequestCreationRestrictedException(format("Cannot create request for event with id=%d, " +
                    "because event has too many participants", eventId));
        }

        User requester = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException(format("Cannot not create new request, because use with id=%d not found", userId)));

        ParticipationRequest request = requestRepo.save(
                new ParticipationRequest(null, event, requester, status, LocalDateTime.now())
        );
        return ParticipationRequestMapper.toDto(request);
    }

    public long countEventConfirmedRequests(long eventId) {
        return requestRepo.countByEvent_IdAndStatus(eventId, CONFIRMED);
    }

    /**
     * Counts the number of confirmed requests for each id in eventIds
     *
     * @return event id to the number of confirmed requests
     */
    public Map<Long, Long> getEventIdToConfirmedRequestsCount(Iterable<Long> eventIds) {
        return requestRepo.findEventIdToRequestsCount(eventIds, CONFIRMED);
    }
}
