package ru.practicum.ewm.stats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.stats.entity.EndpointHitEntity;

import java.time.LocalDateTime;
import java.util.List;

public interface EndpointHitRepository extends JpaRepository<EndpointHitEntity, Long> {

    /**
     * if shouldGetAll is true, return all hits, no matter what's in uris
     *
     * @return <l>
     * <li>object[0] - app</li>
     * <li>object[1] - uri</li>
     * <li>object[2] - hits</li>
     * </l>
     */
    @Query(
            "select e.app, " +
                    "e.uri, " +
                    "count(e.ip) as hits " +
                    "from EndpointHitEntity e " +
                    "where (:shouldGetAll = true or e.uri in(:uris)) and " +
                    "e.timestamp between :start and :end " +
                    "group by e.app, e.uri, e.timestamp " +
                    "order by hits desc"
    )
    List<Object[]> getStatsOrderByHitsDesc(List<String> uris, LocalDateTime start, LocalDateTime end, boolean shouldGetAll);

    /**
     * if shouldGetAll is true, return all hits, no matter what's in uris
     *
     * @return <l>
     * <li>object[0] - app</li>
     * <li>object[1] - uri</li>
     * <li>object[2] - hits</li>
     * </l>
     */
    @Query(
            "select e.app, " +
                    "e.uri, " +
                    "count(distinct e.ip) as hits " +
                    "from EndpointHitEntity e " +
                    "where (:shouldGetAll = true or e.uri in(:uris)) and " +
                    "e.timestamp between :start and :end " +
                    "group by e.app, e.uri, e.timestamp " +
                    "order by hits desc"
    )
    List<Object[]> getStatsCountUniqueIpOrderByHitsDesc(List<String> uris, LocalDateTime start, LocalDateTime end, boolean shouldGetAll);
}
