package com.ineat.colistracker.infrastructure.database.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "colis")
public class ColisEntity {
    @Id
    @Column(name = "id", columnDefinition = "SERIAL")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String name;

    @Column(name = "tracking_id")
    public String trackingId;

    @CreationTimestamp
    public LocalDateTime created;

    @UpdateTimestamp
    public LocalDateTime updated;

}
