package ru.practicum.ewm.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.stats.contant.Patterns;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventAdminRequest {

    @Size(min = 20, max = 2000)
    private String annotation;

    private Long category;

    @Size(min = 20, max = 7000)
    private String description;

    @JsonFormat(pattern = Patterns.dateTimePattern)
    private LocalDateTime eventDate;

    @Valid
    private Location location;

    private Boolean paid;
    private Integer participantLimit;
    private Boolean requestModeration;

    private StateAction stateAction;

    @Size(min = 3, max = 120)
    private String title;


    public enum StateAction {
        PUBLISH_EVENT,
        REJECT_EVENT
    }
}

