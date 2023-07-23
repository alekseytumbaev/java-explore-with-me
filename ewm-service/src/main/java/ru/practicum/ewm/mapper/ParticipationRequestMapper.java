package ru.practicum.ewm.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.model.dto.ParticipationRequestDto;
import ru.practicum.ewm.model.entity.ParticipationRequest;

@UtilityClass
public class ParticipationRequestMapper {

    public ParticipationRequestDto toDto(ParticipationRequest request) {
        return new ParticipationRequestDto(
                request.getCreated(),
                request.getEvent().getId(),
                request.getId(),
                request.getRequester().getId(),
                request.getStatus()
        );
    }
}
