package ru.practicum.ewm.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRequestStatusUpdateResult {

    @Valid
    private List<ParticipationRequestDto> confirmedRequests;

    @Valid
    private List<ParticipationRequestDto> rejectedRequests;
}

