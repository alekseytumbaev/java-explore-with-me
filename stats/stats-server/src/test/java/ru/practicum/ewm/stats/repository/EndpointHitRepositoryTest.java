package ru.practicum.ewm.stats.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.practicum.ewm.stats.entity.EndpointHitEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AutoConfigureTestDatabase
@DataJpaTest
class EndpointHitRepositoryTest {

    private final EndpointHitRepository endpointHitRepository;

    @Autowired
    EndpointHitRepositoryTest(EndpointHitRepository endpointHitRepository) {
        this.endpointHitRepository = endpointHitRepository;
    }

    @Test
    void getStatsCountAllIp() {
        LocalDateTime now = LocalDateTime.now();
        String uri1 = "/events";
        String uri2 = "/events/5";
        endpointHitRepository.save(new EndpointHitEntity(null, "app", uri1, "ip1", now));
        endpointHitRepository.save(new EndpointHitEntity(null, "app", uri1, "ip1", now));
        endpointHitRepository.save(new EndpointHitEntity(null, "app", uri2, "ip1", now));

        List<Object[]> stats = endpointHitRepository
                .getStatsOrderByHitsDesc(List.of(uri1, uri2), now.minusDays(1), now.plusDays(2), false);
        assertEquals(2, stats.size(), "Stats size should be 2");

        assertEquals(2L, stats.get(0)[2], "First stats should have 2 hits");
    }

    @Test
    void getStatsCountUniqueIp() {
        LocalDateTime now = LocalDateTime.now();
        String uri1 = "/events";
        String uri2 = "/events/5";
        endpointHitRepository.save(new EndpointHitEntity(null, "app", uri1, "ip1", now));
        endpointHitRepository.save(new EndpointHitEntity(null, "app", uri1, "ip1", now));
        endpointHitRepository.save(new EndpointHitEntity(null, "app", uri2, "ip1", now));

        List<Object[]> stats = endpointHitRepository
                .getStatsCountUniqueIpOrderByHitsDesc(List.of(uri1, uri2), now.minusDays(1), now.plusDays(2), false);
        assertEquals(2, stats.size(), "Stats size should be 2");
        assertEquals(1L, stats.get(0)[2], "First stats should have 1 hit");
        assertEquals(1L, stats.get(1)[2], "Second stats should have 1 hit");
    }
}