package ru.practicum.ewm.model.entity;

import lombok.*;
import ru.practicum.ewm.model.dto.ParticipationRequestStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "participation_requests")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ParticipationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User requester;

    @Enumerated(EnumType.STRING)
    private ParticipationRequestStatus status;

    private LocalDateTime created;
}
