package ru.practicum.ewm.mapper;

import lombok.Value;
import lombok.experimental.UtilityClass;
import ru.practicum.ewm.model.dto.*;
import ru.practicum.ewm.model.entity.Category;
import ru.practicum.ewm.model.entity.Event;
import ru.practicum.ewm.model.entity.User;

import java.time.LocalDateTime;

@UtilityClass
public class EventMapper {
    public Event toEntity(NewEventDto newEventDto, Category category, User initiator, EventState state) {
        return new Event(
                null,
                newEventDto.getAnnotation(),
                category,
                newEventDto.getDescription(),
                newEventDto.getLocation().getLon(),
                newEventDto.getLocation().getLat(),
                newEventDto.getPaid(),
                newEventDto.getParticipantLimit(),
                newEventDto.getRequestModeration(),
                newEventDto.getTitle(),
                newEventDto.getEventDate(),
                LocalDateTime.now(),
                null,
                initiator,
                state
        );
    }

    public EventFullDto toFullDto(Event event, CategoryDto categoryDto, long confirmedRequests,
                                  UserShortDto initiator, long views) {
        return new EventFullDto(
                event.getAnnotation(),
                categoryDto,
                confirmedRequests,
                event.getCreatedOn(),
                event.getDescription(),
                event.getEventDate(),
                event.getId(),
                initiator,
                new Location(event.getLatitude(), event.getLongitude()),
                event.isPaid(),
                event.getParticipantLimit(),
                event.getPublishedOn(),
                event.isRequestModeration(),
                event.getState(),
                event.getTitle(),
                views
        );
    }

    public EventShortDto toShortDto(Event event, CategoryDto categoryDto, long confirmedRequests,
                                    UserShortDto initiator, long views) {
        return new EventShortDto(
                event.getAnnotation(),
                categoryDto,
                confirmedRequests,
                event.getEventDate(),
                event.getId(),
                initiator,
                event.isPaid(),
                event.getTitle(),
                views
        );
    }

    /**
     * Converts {@link UpdateEventUserRequest} to {@link Event}.
     * If some fields in updateEventUserRequest are null, they will be set to values form previousEvent
     *
     * @param updateEventUserRequest dto with new data, fields that don't require update should be null
     * @param category               new category or null if it didn't change
     * @param previousEvent          entity with previous data
     * @return entity to update
     */
    public Event toUpdatedEntity(UpdateEventUserRequest updateEventUserRequest, Category category, Event previousEvent) {
        Event event = copyEvent(previousEvent);
        CommonUpdateFields common = new CommonUpdateFields(
                category,
                updateEventUserRequest.getAnnotation(),
                updateEventUserRequest.getDescription(),
                updateEventUserRequest.getEventDate(),
                updateEventUserRequest.getLocation(),
                updateEventUserRequest.getPaid(),
                updateEventUserRequest.getParticipantLimit(),
                updateEventUserRequest.getRequestModeration()
        );
        setCommonUpdateFields(event, common);
        if (updateEventUserRequest.getStateAction() != null) {
            UpdateEventUserRequest.StateActionEnum stateAction = updateEventUserRequest.getStateAction();
            if (stateAction.equals(UpdateEventUserRequest.StateActionEnum.SEND_TO_REVIEW)) {
                event.setState(EventState.PENDING);
            } else if (stateAction.equals(UpdateEventUserRequest.StateActionEnum.CANCEL_REVIEW)) {
                event.setState(EventState.CANCELED);
            }
        }
        if (updateEventUserRequest.getTitle() != null) {
            event.setTitle(updateEventUserRequest.getTitle());
        }
        return event;
    }

    public Event toUpdatedEntity(UpdateEventAdminRequest updateEventAdminRequest, Category category, Event previousEvent) {
        Event event = copyEvent(previousEvent);
        CommonUpdateFields common = new CommonUpdateFields(
                category,
                updateEventAdminRequest.getAnnotation(),
                updateEventAdminRequest.getDescription(),
                updateEventAdminRequest.getEventDate(),
                updateEventAdminRequest.getLocation(),
                updateEventAdminRequest.getPaid(),
                updateEventAdminRequest.getParticipantLimit(),
                updateEventAdminRequest.getRequestModeration()
        );
        setCommonUpdateFields(event, common);
        if (updateEventAdminRequest.getStateAction() != null) {
            UpdateEventAdminRequest.StateAction stateAction = updateEventAdminRequest.getStateAction();
            if (stateAction.equals(UpdateEventAdminRequest.StateAction.PUBLISH_EVENT)) {
                event.setState(EventState.PUBLISHED);
            } else if (stateAction.equals(UpdateEventAdminRequest.StateAction.REJECT_EVENT)) {
                event.setState(EventState.CANCELED);
            }
        }
        if (updateEventAdminRequest.getTitle() != null) {
            event.setTitle(updateEventAdminRequest.getTitle());
        }
        return event;
    }


    private static void setCommonUpdateFields(Event event, CommonUpdateFields common) {
        if (common.annotation != null) {
            event.setAnnotation(common.annotation);
        }
        if (common.category != null) {
            event.setCategory(common.category);
        }
        if (common.description != null) {
            event.setDescription(common.description);
        }
        if (common.eventDate != null) {
            event.setEventDate(common.eventDate);
        }
        if (common.location != null) {
            event.setLongitude(common.location.getLon());
            event.setLatitude(common.location.getLat());
        }
        if (common.paid != null) {
            event.setPaid(common.paid);
        }
        if (common.participantLimit != null) {
            event.setParticipantLimit(common.participantLimit);
        }
        if (common.requestModeration != null) {
            event.setRequestModeration(common.requestModeration);
        }
    }

    private Event copyEvent(Event event) {
        return new Event(
                event.getId(),
                event.getAnnotation(),
                event.getCategory(),
                event.getDescription(),
                event.getLongitude(),
                event.getLatitude(),
                event.isPaid(),
                event.getParticipantLimit(),
                event.isRequestModeration(),
                event.getTitle(),
                event.getEventDate(),
                event.getCreatedOn(),
                event.getPublishedOn(),
                event.getInitiator(),
                event.getState()
        );
    }


    @Value
    private class CommonUpdateFields {
        Category category;
        String annotation;
        String description;
        LocalDateTime eventDate;
        Location location;
        Boolean paid;
        Integer participantLimit;
        Boolean requestModeration;
    }
}
