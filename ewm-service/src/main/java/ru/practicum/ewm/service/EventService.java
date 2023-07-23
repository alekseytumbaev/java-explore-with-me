package ru.practicum.ewm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.StatsClient;
import ru.practicum.ewm.error.exception.*;
import ru.practicum.ewm.mapper.CategoryMapper;
import ru.practicum.ewm.mapper.EventMapper;
import ru.practicum.ewm.mapper.UserMapper;
import ru.practicum.ewm.model.dto.*;
import ru.practicum.ewm.model.entity.Category;
import ru.practicum.ewm.model.entity.Event;
import ru.practicum.ewm.model.entity.User;
import ru.practicum.ewm.model.parameter.EventParameter;
import ru.practicum.ewm.repository.EventRepository;
import ru.practicum.ewm.repository.specification.EventSpecifications;
import ru.practicum.ewm.stats.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.*;

import static java.lang.String.format;
import static ru.practicum.ewm.model.dto.EventState.*;
import static ru.practicum.ewm.model.dto.UpdateEventAdminRequest.StateAction.PUBLISH_EVENT;
import static ru.practicum.ewm.model.dto.UpdateEventAdminRequest.StateAction.REJECT_EVENT;

@Service
@RequiredArgsConstructor
@Transactional
public class EventService {

    private static final String EVENT_URI_PATTERN = "/events/%d";
    private static final int EVENT_ID_POSITION_IN_URI = 2;
    private static final int MINIMUM_HOURS_BEFORE_EVENT_START_USER = 2;
    private static final int MINIMUM_HOURS_BEFORE_EVENT_START_ADMIN = 1;

    private final EventRepository eventRepo;
    private final UserService userService;
    private final CategoryService categoryService;
    private final ParticipationRequestService requestService;
    private final StatsClient statsClient;

    /**
     * Add event with status pending
     */
    public EventFullDto addEvent(Long userId, NewEventDto newEventDto) {
        checkEventStartTime(newEventDto.getEventDate(), MINIMUM_HOURS_BEFORE_EVENT_START_USER);
        User user = userService.getEntityById(userId);
        Category category = categoryService.getEntityById(newEventDto.getCategory());

        Event event = eventRepo.save(EventMapper.toEntity(newEventDto, category, user, PENDING));
        return EventMapper.toFullDto(
                event,
                CategoryMapper.toDto(event.getCategory()),
                0,
                UserMapper.toShortDto(event.getInitiator()),
                0
        );
    }

    public List<EventShortDto> getAllEventsByInitiatorId(Long initiatorId, Integer from, Integer size) {
        EventParameter eventParameter = EventParameter.builder().initiatorIds(List.of(initiatorId)).build();
        PageRequest pageRequest = PageRequest.of(from / size, size);
        Page<Event> events = eventRepo.findAll(EventSpecifications.where(eventParameter), pageRequest);
        return toShortDtos(events, false);
    }

    public List<EventFullDto> getAllEventsAdmin(EventParameter eventParameter, Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        Page<Event> events = eventRepo.findAll(EventSpecifications.where(eventParameter), pageRequest);
        return toFullDtos(events, false);
    }

    public List<EventShortDto> getAllEventsPublic(EventParameter eventParameter, EventSort sort, Integer from, Integer size) {
        LocalDateTime start = eventParameter.getNotBefore();
        LocalDateTime end = eventParameter.getNotAfter();
        if (start != null && end != null && end.isBefore(start)) {
            throw new EndBeforeStartTimeException(format("Cannot get events, because end time before start time is not allowed: " +
                    "start='%s', end='%s'", eventParameter.getNotBefore(), eventParameter.getNotAfter()));
        }

        PageRequest pageRequest = PageRequest.of(from / size, size);
        if (sort != null && sort.equals(EventSort.EVENT_DATE)) {
            pageRequest.withSort(Sort.by("eventDate").descending());
        }
        eventParameter.setStates(List.of(PUBLISHED));
        Page<Event> events = eventRepo.findAll(EventSpecifications.where(eventParameter), pageRequest);

        List<EventShortDto> eventDtos = toShortDtos(events, false);

        if (sort != null && sort.equals(EventSort.VIEWS)) {
            eventDtos.sort(Comparator.comparingLong(EventShortDto::getViews));
        }
        return eventDtos;
    }

    public EventFullDto getEventPublic(Long eventId) {
        EventParameter eventParameter = EventParameter.builder()
                .ids(List.of(eventId))
                .states(List.of(PUBLISHED))
                .build();
        Event event = eventRepo.findOne(EventSpecifications.where(eventParameter)).orElseThrow(() ->
                new EventNotFoundException(format("Event with id=%d not found", eventId))
        );

        return toFullDto(event, true);
    }

    public EventFullDto getEventByInitiatorId(Long initiatorId, Long eventId) {
        Event event = eventRepo.findById(eventId).orElseThrow(() ->
                new EventNotFoundException(
                        format("User with id=%d cannot get event with id=%d, because event not found", initiatorId, eventId))
        );
        if (!initiatorId.equals(event.getInitiator().getId())) {
            throw new UnauthorizedException(
                    format("User with id=%d cannot get event with id=%d, because user is not initiator", initiatorId, eventId));
        }

        return toFullDto(event, false);
    }

    public EventFullDto updateEventAdmin(Long eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        Event event = eventRepo.findById(eventId).orElseThrow(() ->
                new EventNotFoundException(
                        format("Admin cannot update event with id=%d, because event not found", eventId))
        );

        Category category;
        if (updateEventAdminRequest.getCategory() != null) {
            category = categoryService.getEntityById(updateEventAdminRequest.getCategory());
        } else {
            category = event.getCategory();
        }

        if (updateEventAdminRequest.getEventDate() != null) {
            checkEventStartTime(updateEventAdminRequest.getEventDate(), MINIMUM_HOURS_BEFORE_EVENT_START_ADMIN);
        }

        UpdateEventAdminRequest.StateAction stateAction = updateEventAdminRequest.getStateAction();
        if (stateAction != null) {
            //check that state can be changed
            if (stateAction.equals(REJECT_EVENT) && event.getState().equals(PUBLISHED)) {
                throw new EventUpdateRestrictedException(
                        format("Admin cannot update event with id=%d, because event state = %s, " +
                                "published events cannot be rejected", eventId, event.getState()));
            }
            if (stateAction.equals(PUBLISH_EVENT)) {
                if (!event.getState().equals(PENDING)) {
                    throw new EventUpdateRestrictedException(
                            format("Admin cannot update event with id=%d, because event state = %s, " +
                                    "only pending events can be published", eventId, event.getState()));
                }
            }

            //set new state after successful checks
            if (stateAction.equals(REJECT_EVENT)) {
                event.setState(CANCELED);
            } else {
                event.setPublishedOn(LocalDateTime.now());
                event.setState(PUBLISHED);
            }
        }

        Event updatedEvent = eventRepo.save(EventMapper.toUpdatedEntity(updateEventAdminRequest, category, event));
        return toFullDto(updatedEvent, false);
    }

    public EventFullDto updateEvent(Long initiatorId, Long eventId, UpdateEventUserRequest updateEventUserRequest) {
        Event event = eventRepo.findById(eventId).orElseThrow(() ->
                new EventNotFoundException(
                        format("User with id=%d cannot update event with id=%d, because event not found", initiatorId, eventId))
        );
        if (!initiatorId.equals(event.getInitiator().getId())) {
            throw new UnauthorizedException(
                    format("User with id=%d cannot update event with id=%d, because user is not initiator", initiatorId, eventId));
        }
        EventState currState = event.getState();
        if (!currState.equals(PENDING) && !currState.equals(CANCELED)) {
            throw new EventUpdateRestrictedException(
                    format("Initiator with id=%d cannot update event with id=%d, because event state = %s, " +
                            "only pending or canceled events can be updated", initiatorId, eventId, currState));
        }

        Category category;
        if (updateEventUserRequest.getCategory() != null) {
            category = categoryService.getEntityById(updateEventUserRequest.getCategory());
        } else {
            category = event.getCategory();
        }

        if (updateEventUserRequest.getEventDate() != null) {
            checkEventStartTime(updateEventUserRequest.getEventDate(), MINIMUM_HOURS_BEFORE_EVENT_START_USER);
        }

        Event updatedEvent = eventRepo.save(EventMapper.toUpdatedEntity(updateEventUserRequest, category, event));
        return toFullDto(updatedEvent, false);
    }


    public List<Event> findEntitiesById(Iterable<Long> eventIds) {
        if (eventIds == null || !eventIds.iterator().hasNext()) {
            return List.of();
        }
        return eventRepo.findAllById(eventIds);
    }

    /**
     * @param uniqueIps when counting event views, count only unique ips if true
     */
    public EventFullDto toFullDto(Event event, boolean uniqueIps) {
        return toFullDtos(List.of(event), uniqueIps).get(0);
    }

    /**
     * @param uniqueIps when counting event views, count only unique ips if true
     */
    public List<EventFullDto> toFullDtos(Iterable<Event> events, boolean uniqueIps) {
        if (!events.iterator().hasNext()) {
            return List.of();
        }

        List<Long> eventIds = eventsToIds(events);
        Map<Long, Long> eventIdToRequestsCount = requestService.getEventIdToConfirmedRequestsCount(eventIds);
        Map<Long, Long> eventIdToViews = getEventIdToViews(eventIds, uniqueIps);

        List<EventFullDto> shortDtos = new ArrayList<>(eventIds.size());
        for (Event event : events) {
            shortDtos.add(
                    EventMapper.toFullDto(
                            event,
                            CategoryMapper.toDto(event.getCategory()),
                            eventIdToRequestsCount.get(event.getId()),
                            UserMapper.toShortDto(event.getInitiator()),
                            eventIdToViews.get(event.getId())
                    )
            );
        }

        return shortDtos;
    }

    /**
     * @param uniqueIps when counting event views, count only unique ips if true
     */
    public List<EventShortDto> toShortDtos(Iterable<Event> events, boolean uniqueIps) {
        if (!events.iterator().hasNext()) {
            return List.of();
        }

        List<Long> eventIds = eventsToIds(events);
        Map<Long, Long> eventIdToRequestsCount = requestService.getEventIdToConfirmedRequestsCount(eventIds);
        Map<Long, Long> eventIdToViews = getEventIdToViews(eventIds, uniqueIps);

        List<EventShortDto> shortDtos = new ArrayList<>(eventIds.size());
        for (Event event : events) {
            shortDtos.add(
                    EventMapper.toShortDto(
                            event,
                            CategoryMapper.toDto(event.getCategory()),
                            eventIdToRequestsCount.get(event.getId()),
                            UserMapper.toShortDto(event.getInitiator()),
                            eventIdToViews.get(event.getId())
                    )
            );
        }

        return shortDtos;
    }

    private List<Long> eventsToIds(Iterable<Event> events) {
        List<Long> eventIds = new LinkedList<>();
        for (Event event : events) {
            eventIds.add(event.getId());
        }
        return eventIds;
    }

    /**
     * Finds number of views for each event
     *
     * @param uniqueIps count only unique ips if true
     * @return Map with event ids to views. If stats for some event not found, views will be 0 for this event
     */
    public Map<Long, Long> getEventIdToViews(Iterable<Long> eventIds, boolean uniqueIps) {
        LocalDateTime start = LocalDateTime.of(1000, 1, 1, 0, 0);
        LocalDateTime end = LocalDateTime.of(9999, 12, 31, 23, 59);
        return getEventIdToViews(eventIds, start, end, uniqueIps);
    }

    /**
     * Finds number of views for each event. Only views that were made from start to end will be counted
     *
     * @param uniqueIps count only unique ips if true
     * @param start     start time of the range
     * @param end       end time of the range
     * @return Map with event ids to views. Empty map if eventIds is empty.
     * If stats for some event not found, views will be 0 for this event.
     */
    public Map<Long, Long> getEventIdToViews(Iterable<Long> eventIds, LocalDateTime start, LocalDateTime end, boolean uniqueIps) {
        if (!eventIds.iterator().hasNext()) {
            return Map.of();
        }

        Map<Long, Long> idsToViews = new HashMap<>();
        List<String> eventUris = new LinkedList<>();
        for (Long id : eventIds) {
            eventUris.add(format(EVENT_URI_PATTERN, id));
            idsToViews.put(id, 0L);
        }

        Optional<ViewStats[]> statsOpt = statsClient.getStats(start, end, eventUris, uniqueIps);
        if (statsOpt.isEmpty()) {
            throw new CannotGetStatsException(format("Cannot get stats eventIds. Start time: %s, end time: %s uris: %s",
                    start, end, eventUris));
        }

        ViewStats[] stats = statsOpt.get();
        for (ViewStats stat : stats) {
            long eventId = Long.parseLong(stat.getUri().split("/")[EVENT_ID_POSITION_IN_URI]);
            idsToViews.put(eventId, stat.getHits());
        }
        return idsToViews;
    }

    /**
     * Checks that event start time is not too early
     *
     * @throws IllegalEventStartTimeException if event start time is too early
     */
    private void checkEventStartTime(LocalDateTime eventStartTime, int minHoursBeforeStart) throws IllegalEventStartTimeException {
        LocalDateTime someHoursForward = LocalDateTime.now().plusHours(minHoursBeforeStart);
        if (eventStartTime.isBefore(someHoursForward)) {
            throw new IllegalEventStartTimeException(
                    format("Event start must be at least %d hour(s) after current time. " +
                                    "Event start time = %s, current time = %s",
                            minHoursBeforeStart, eventStartTime, LocalDateTime.now())
            );
        }
    }
}
