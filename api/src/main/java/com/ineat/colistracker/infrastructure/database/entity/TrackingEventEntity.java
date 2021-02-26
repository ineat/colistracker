package com.ineat.colistracker.infrastructure.database.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tracking_event")
public class TrackingEventEntity {
    @Id
    @Column(name = "id", columnDefinition = "SERIAL")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "time", columnDefinition = "TIMESTAMP", nullable = false)
    public LocalDateTime time;

    @Column(name = "description", columnDefinition = "TEXT")
    public String description;

    @ManyToOne(fetch = FetchType.LAZY)
    public TrackingEntity tracking;
}
