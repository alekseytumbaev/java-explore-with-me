package ru.practicum.ewm.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.stats.contant.Patterns;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto {

    @NotNull
    private String annotation;

    @NotNull
    @Valid
    private CategoryDto category;

    private Long confirmedRequests;

    @JsonFormat(pattern = Patterns.dateTimePattern)
    private LocalDateTime createdOn;

    private String description;

    @NotNull
    @JsonFormat(pattern = Patterns.dateTimePattern)
    private LocalDateTime eventDate;

    private Long id;

    @NotNull
    @Valid
    private UserShortDto initiator;

    @NotNull
    @Valid
    private Location location;

    @NotNull
    private Boolean paid;

    private Integer participantLimit = 0;

    private LocalDateTime publishedOn;

    private Boolean requestModeration = true;

    private EventState state;

    @NotNull
    private String title;

    private Long views;
}

