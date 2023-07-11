package ru.practicum.ewm.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.model.dto.*;
import ru.practicum.ewm.model.entity.Category;
import ru.practicum.ewm.model.entity.Event;
import ru.practicum.ewm.model.entity.User;

import java.time.LocalDateTime;

@UtilityClass
public class EventMapper {
    public Event toEntity(NewEventDto newEventDto, Category category, User initiator, EventFullDto.StateEnum state) {
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
        if (updateEventUserRequest.getAnnotation() != null) {
            event.setAnnotation(updateEventUserRequest.getAnnotation());
        }
        if (updateEventUserRequest.getCategory() != null && category != null) {
            event.setCategory(category);
        }
        if (updateEventUserRequest.getDescription() != null) {
            event.setDescription(updateEventUserRequest.getDescription());
        }
        if (updateEventUserRequest.getEventDate() != null) {
            event.setEventDate(updateEventUserRequest.getEventDate());
        }
        if (updateEventUserRequest.getLocation() != null) {
            Location newLocation = updateEventUserRequest.getLocation();
            event.setLongitude(newLocation.getLon());
            event.setLatitude(newLocation.getLat());
        }
        if (updateEventUserRequest.getPaid() != null) {
            event.setPaid(updateEventUserRequest.getPaid());
        }
        if (updateEventUserRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventUserRequest.getParticipantLimit());
        }
        if (updateEventUserRequest.getRequestModeration() != null) {
            event.setRequestModeration(updateEventUserRequest.getRequestModeration());
        }
        if (updateEventUserRequest.getStateAction() != null) {
            UpdateEventUserRequest.StateActionEnum stateAction = updateEventUserRequest.getStateAction();
            if (stateAction.equals(UpdateEventUserRequest.StateActionEnum.SEND_TO_REVIEW)) {
                event.setState(EventFullDto.StateEnum.PENDING);
            } else if (stateAction.equals(UpdateEventUserRequest.StateActionEnum.CANCEL_REVIEW)) {
                event.setState(EventFullDto.StateEnum.CANCELED);
            }
        }
        if (updateEventUserRequest.getTitle() != null) {
            event.setTitle(updateEventUserRequest.getTitle());
        }
        return event;
    }

    public Event toUpdatedEntity(UpdateEventAdminRequest updateEventAdminRequest, Category category, Event previousEvent) {
        Event event = copyEvent(previousEvent);
        if (updateEventAdminRequest.getAnnotation() != null) {
            event.setAnnotation(updateEventAdminRequest.getAnnotation());
        }
        if (updateEventAdminRequest.getCategory() != null && category != null) {
            event.setCategory(category);
        }
        if (updateEventAdminRequest.getDescription() != null) {
            event.setDescription(updateEventAdminRequest.getDescription());
        }
        if (updateEventAdminRequest.getEventDate() != null) {
            event.setEventDate(updateEventAdminRequest.getEventDate());
        }
        if (updateEventAdminRequest.getLocation() != null) {
            Location newLocation = updateEventAdminRequest.getLocation();
            event.setLongitude(newLocation.getLon());
            event.setLatitude(newLocation.getLat());
        }
        if (updateEventAdminRequest.getPaid() != null) {
            event.setPaid(updateEventAdminRequest.getPaid());
        }
        if (updateEventAdminRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventAdminRequest.getParticipantLimit());
        }
        if (updateEventAdminRequest.getRequestModeration() != null) {
            event.setRequestModeration(updateEventAdminRequest.getRequestModeration());
        }
        if (updateEventAdminRequest.getStateAction() != null) {
            UpdateEventAdminRequest.StateActionEnum stateAction = updateEventAdminRequest.getStateAction();
            if (stateAction.equals(UpdateEventAdminRequest.StateActionEnum.PUBLISH_EVENT)) {
                event.setState(EventFullDto.StateEnum.PUBLISHED);
            } else if (stateAction.equals(UpdateEventAdminRequest.StateActionEnum.REJECT_EVENT)) {
                event.setState(EventFullDto.StateEnum.CANCELED);
            }
        }
        if (updateEventAdminRequest.getTitle() != null) {
            event.setTitle(updateEventAdminRequest.getTitle());
        }
        return event;
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
}
