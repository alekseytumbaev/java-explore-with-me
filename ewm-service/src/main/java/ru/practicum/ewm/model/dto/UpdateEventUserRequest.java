package ru.practicum.ewm.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewm.stats.contant.Patterns;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventUserRequest {

    @Size(min = 20, max = 2000)
    private String annotation;

    private Long category;

    @Size(min = 20, max = 7000)
    private String description;

    @JsonFormat(pattern = Patterns.dateTimePattern)
    private LocalDateTime eventDate;

    private Location location;

    private Boolean paid;

    private Integer participantLimit;
    private Boolean requestModeration;
    private StateActionEnum stateAction;

    @Size(min = 3, max = 120)
    private String title;


    public enum StateActionEnum {
        SEND_TO_REVIEW,
        CANCEL_REVIEW
    }
}

