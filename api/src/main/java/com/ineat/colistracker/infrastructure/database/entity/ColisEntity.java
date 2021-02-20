package org.acme.infrastructure.database.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ColisEntity {
    @Id
    public Long id;
    public String name;
    public String trackingId;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    public LocalDateTime created;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    public LocalDateTime updated;

}
