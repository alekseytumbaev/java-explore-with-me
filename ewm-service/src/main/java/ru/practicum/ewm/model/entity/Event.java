package ru.practicum.ewm.model.entity;

import lombok.*;
import ru.practicum.ewm.model.dto.EventState;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String annotation;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Column
    private String description;

    private float longitude;
    private float latitude;
    private boolean paid;
    private int participantLimit;
    private boolean requestModeration;

    @Column
    private String title;

    private LocalDateTime eventDate;
    private LocalDateTime createdOn;
    private LocalDateTime publishedOn;

    @ManyToOne
    @JoinColumn(name = "initiator_id")
    private User initiator;

    @Enumerated(EnumType.STRING)
    private EventState state;
}
