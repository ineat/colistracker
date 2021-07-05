package com.ineat.colistracker.infrastructure.database.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "tracking")
public class TrackingEntity {
    @Id
    @Column(name = "id", columnDefinition = "SERIAL")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    @Column(name = "tracking_id")
    public String trackingId;

    @CreationTimestamp
    public LocalDateTime created;

    public LocalDateTime updated;

    @OneToMany(
            mappedBy = "tracking",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    public Set<TrackingEventEntity> trackingHistory;

}
