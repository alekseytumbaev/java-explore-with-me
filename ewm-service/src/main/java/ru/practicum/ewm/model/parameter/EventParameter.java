package ru.practicum.ewm.model.parameter;

import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm.model.dto.EventFullDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Parameters by which resulting events will be filtered.
 * For example if categoryIds = {1,2} only events with categories 1 and 2 will be retrieved
 */
@Data
@Builder
public class EventParameter {

    private List<Long> ids;
    private List<Long> categoryIds;
    private List<Long> initiatorIds;
    private List<EventFullDto.StateEnum> states;

    /**
     * Event date should not be before this date
     */
    private LocalDateTime notBefore;

    /**
     * Event date should not be after this date
     */
    private LocalDateTime notAfter;

    /**
     * Resulting events will be searched by annotation or description containing this text ignore case
     */
    private String searchText;

    private Boolean available;
    private Boolean paid;
}
