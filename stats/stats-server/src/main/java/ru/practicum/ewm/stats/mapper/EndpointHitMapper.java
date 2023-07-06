package ru.practicum.ewm.stats.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewm.stats.dto.EndpointHit;
import ru.practicum.ewm.stats.entity.EndpointHitEntity;

@UtilityClass
public class EndpointHitMapper {
    public EndpointHitEntity toEntity(EndpointHit endpointHit) {
        return new EndpointHitEntity(
                endpointHit.getId(),
                endpointHit.getApp(),
                endpointHit.getUri(),
                endpointHit.getIp(),
                endpointHit.getTimestamp()
        );
    }

    public EndpointHit toDto(EndpointHitEntity endpointHitEntity) {
        return new EndpointHit(
                endpointHitEntity.getId(),
                endpointHitEntity.getApp(),
                endpointHitEntity.getUri(),
                endpointHitEntity.getIp(),
                endpointHitEntity.getTimestamp()
        );
    }
}
