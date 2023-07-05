package ru.practicum.ewm.stats.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewm.stats.dto.EndpointHit;
import ru.practicum.ewm.stats.dto.ViewStats;
import ru.practicum.ewm.stats.error.exception.EndBeforeStartTimeException;
import ru.practicum.ewm.stats.mapper.EndpointHitMapper;
import ru.practicum.ewm.stats.mapper.ViewStatsMapper;
import ru.practicum.ewm.stats.entity.EndpointHitEntity;
import ru.practicum.ewm.stats.repository.EndpointHitRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class StatsService {
    private final EndpointHitRepository statsRepo;

    @Transactional
    public EndpointHit saveStats(EndpointHit endpointHit) {
        EndpointHitEntity entity = statsRepo.save(EndpointHitMapper.toEntity(endpointHit));
        return EndpointHitMapper.toDto(entity);
    }

    @Transactional(readOnly = true)
    public List<ViewStats> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        if (end.isBefore(start)) {
            throw new EndBeforeStartTimeException(
                    format("Cannot get stats from %s to %s, because end is before start", start, end));
        }
        boolean shouldGetAll = uris == null || uris.isEmpty();
        List<Object[]> statsAsObj;
        if (unique) {
            statsAsObj = statsRepo.getStatsCountUniqueIpOrderByHitsDesc(uris, start, end, shouldGetAll);
        } else {
            statsAsObj = statsRepo.getStatsOrderByHitsDesc(uris, start, end, shouldGetAll);
        }

        return statsAsObj.stream().map(ViewStatsMapper::toViewStats).collect(Collectors.toList());
    }
}
