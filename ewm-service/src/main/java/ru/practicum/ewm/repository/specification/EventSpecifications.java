package ru.practicum.ewm.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.practicum.ewm.model.dto.EventFullDto;
import ru.practicum.ewm.model.dto.ParticipationRequestStatus;
import ru.practicum.ewm.model.entity.Event;
import ru.practicum.ewm.model.entity.ParticipationRequest;
import ru.practicum.ewm.model.parameter.EventParameter;

import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.time.LocalDateTime;
import java.util.Collection;

public final class EventSpecifications {

    public static Specification<Event> where(EventParameter parameter) {
        Specification<Event> spec = Specification.where(empty());
        if (parameter.getIds() != null && !parameter.getIds().isEmpty()) {
            spec = spec.and(idIn(parameter.getIds()));
        }
        if (parameter.getCategoryIds() != null && !parameter.getCategoryIds().isEmpty()) {
            spec = spec.and(categoryIn(parameter.getCategoryIds()));
        }
        if (parameter.getInitiatorIds() != null && !parameter.getInitiatorIds().isEmpty()) {
            spec = spec.and(initiatorIn(parameter.getInitiatorIds()));
        }
        if (parameter.getStates() != null && !parameter.getStates().isEmpty()) {
            spec = spec.and(stateIn(parameter.getStates()));
        }
        if (parameter.getNotBefore() != null) {
            spec = spec.and(notBefore(parameter.getNotBefore()));
        }
        if (parameter.getNotAfter() != null) {
            spec = spec.and(notAfter(parameter.getNotAfter()));
        }
        if (parameter.getSearchText() != null && !parameter.getSearchText().isBlank()) {
            spec = spec.and(annotationOrDescriptionLike(parameter.getSearchText()));
        }
        if (parameter.getAvailable() != null) {
            spec = spec.and(isAvailable(parameter.getAvailable()));
        }
        if (parameter.getPaid() != null) {
            spec = spec.and(isPaid(parameter.getPaid()));
        }

        return spec;
    }

    private static Specification<Event> idIn(Collection<Long> ids) {
        return (root, query, cb) -> cb.in(root.get("id")).value(ids);
    }

    private static Specification<Event> categoryIn(Collection<Long> categoryIds) {
        return (root, query, cb) -> cb.in(root.get("category").get("id")).value(categoryIds);
    }

    private static Specification<Event> initiatorIn(Collection<Long> initiatorIds) {
        return (root, query, cb) -> cb.in(root.get("initiator").get("id")).value(initiatorIds);
    }

    private static Specification<Event> stateIn(Collection<EventFullDto.StateEnum> states) {
        return (root, query, cb) -> cb.in(root.get("state")).value(states);
    }

    private static Specification<Event> notBefore(LocalDateTime dateTime) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("eventDate"), dateTime);
    }

    private static Specification<Event> notAfter(LocalDateTime dateTime) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("eventDate"), dateTime);
    }

    /**
     * Case-insensitive
     */
    private static Specification<Event> annotationOrDescriptionLike(String text) {
        return (root, query, cb) -> {
            String pattern = "%" + text.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("annotation")), pattern),
                    cb.like(cb.lower(root.get("description")), pattern)
            );
        };
    }

    private static Specification<Event> isAvailable(boolean available) {
        return (root, query, cb) -> {
            if (!available) {
                return cb.conjunction();
            }

            Subquery<Long> confirmedRequestsSubquery = query.subquery(Long.class);
            Root<ParticipationRequest> requestRoot = confirmedRequestsSubquery.from(ParticipationRequest.class);
            confirmedRequestsSubquery.select(cb.count(requestRoot));
            confirmedRequestsSubquery.where(cb.and(
                    cb.equal(requestRoot.get("event"), root),
                    cb.equal(requestRoot.get("status"), ParticipationRequestStatus.CONFIRMED)
            ));

            Predicate participantLimitIsZero = cb.equal(root.get("participantLimit"), 0);
            Predicate numberOfConfirmedRequestsIsLessThanParticipantLimit = cb.lt(confirmedRequestsSubquery, root.get("participantLimit"));

            return cb.or(participantLimitIsZero, numberOfConfirmedRequestsIsLessThanParticipantLimit);
        };
    }


    private static Specification<Event> isPaid(boolean paid) {
        return (root, query, cb) -> cb.equal(root.get("paid"), paid);
    }

    private static Specification<Event> empty() {
        return (root, query, cb) -> cb.conjunction();
    }


    private EventSpecifications() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
