package ru.practicum.ewm.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.model.dto.ParticipationRequestDto;
import ru.practicum.ewm.model.entity.Event;
import ru.practicum.ewm.model.entity.ParticipationRequest;
import ru.practicum.ewm.model.entity.User;

@UtilityClass
public class ParticipationRequestMapper {

    public ParticipationRequest toEntity(ParticipationRequestDto requestDto, Event event, User requester) {
        return new ParticipationRequest(
                requestDto.getId(),
                event,
                requester,
                requestDto.getStatus(),
                requestDto.getCreated()
        );
    }

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
