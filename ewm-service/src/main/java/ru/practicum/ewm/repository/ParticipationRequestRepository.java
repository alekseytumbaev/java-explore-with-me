package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.model.dto.ParticipationRequestStatus;
import ru.practicum.ewm.model.entity.ParticipationRequest;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {

    List<ParticipationRequest> findAllByEvent_Initiator_IdAndEvent_Id(Long eventInitiatorId, Long eventId);

    List<ParticipationRequest> findAllByEvent_Initiator_IdAndEvent_IdAndIdIn(Long requesterId, Long eventId, List<Long> requestIds);

    List<ParticipationRequest> findAllByRequester_Id(Long userId);

    Optional<ParticipationRequest> findByIdAndRequester_Id(Long requestId, Long userId);

    long countByEvent_IdAndStatus(Long eventId, ParticipationRequestStatus participationRequestStatus);

    boolean existsByRequester_IdAndEvent_Id(Long userId, Long eventId);


    @Query(value =
            "SELECT e.id, COUNT(pr.id) " +
                    "FROM events e " +
                    "LEFT JOIN participation_requests pr ON e.id = pr.event_id AND pr.status = :participationRequestStatus " +
                    "WHERE e.id IN :eventIds " +
                    "GROUP BY e.id",
            nativeQuery = true)
    List<Object[]> findEventIdToRequestsCountAsList(Iterable<Long> eventIds, String participationRequestStatus);

    /**
     * Counts the number of requests with passed status for each id in eventIds
     *
     * @return event id to the number of requests with a given status
     */
    default Map<Long, Long> findEventIdToRequestsCount(Iterable<Long> eventIds, ParticipationRequestStatus requestStatus) {
        List<Object[]> eventIdToRequests = findEventIdToRequestsCountAsList(eventIds, requestStatus.toString());
        Map<Long, Long> result = new HashMap<>(eventIdToRequests.size());
        for (Object[] eventIdToRequest : eventIdToRequests) {
            Long eventId = ((BigInteger) eventIdToRequest[0]).longValue();
            Long requestCount = ((BigInteger) eventIdToRequest[1]).longValue();
            result.put(eventId, requestCount);
        }
        return result;
    }


    @Modifying
    @Query("update ParticipationRequest r set r.status = :newStatus " +
            "where r.event.id = :eventId and r.status = :prevStatus")
    int updateAllStatusByEvent_IdAndPreviousStatus(Long eventId, ParticipationRequestStatus prevStatus, ParticipationRequestStatus newStatus);

}
