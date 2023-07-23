package ru.practicum.ewm.controller.open.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.ewm.StatsClient;
import ru.practicum.ewm.controller.open.EventsApi;
import ru.practicum.ewm.model.dto.EventFullDto;
import ru.practicum.ewm.model.dto.EventShortDto;
import ru.practicum.ewm.model.dto.EventSort;
import ru.practicum.ewm.model.parameter.EventParameter;
import ru.practicum.ewm.service.EventService;
import ru.practicum.ewm.stats.dto.EndpointHit;
import ru.practicum.ewm.util.WebUtils;
import ru.practicum.ewm.util.constant.ServiceConstants;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.LocalDateTime.now;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EventController implements EventsApi {

    private final EventService eventService;
    private final StatsClient statsClient;

    @Override
    public EventFullDto getEventPublic(Long id) {
        HttpServletRequest request = WebUtils.getCurrentHttpRequest();
        statsClient.saveStats(
                new EndpointHit(null, ServiceConstants.app, request.getRequestURI(), request.getRemoteAddr(), now())
        );

        EventFullDto eventFullDto = eventService.getEventPublic(id);
        log.info("Event found: {}", eventFullDto);
        return eventFullDto;
    }

    @Override
    public List<EventShortDto> getEventsPublic(String text, List<Long> categories, Boolean paid,
                                               LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                               Boolean onlyAvailable, EventSort sort,
                                               Integer from, Integer size) {
        HttpServletRequest request = WebUtils.getCurrentHttpRequest();
        statsClient.saveStats(
                new EndpointHit(null, ServiceConstants.app, request.getRequestURI(), request.getRemoteAddr(), now())
        );

        EventParameter parameter = EventParameter.builder()
                .searchText(text)
                .categoryIds(categories)
                .paid(paid)
                .notBefore(rangeStart)
                .notAfter(rangeEnd)
                .available(onlyAvailable)
                .build();

        List<EventShortDto> events = eventService.getAllEventsPublic(parameter, sort, from, size);
        log.info("Found {} events with parameters: eventParameter={}, sort={}, from={}, size={}",
                events.size(), parameter, sort, from, size);

        return events;
    }
}
