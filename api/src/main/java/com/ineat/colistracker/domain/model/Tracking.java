package com.ineat.colistracker.domain.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.With;

import java.time.LocalDateTime;
import java.util.Set;

@With
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tracking {
    /**
     * Technical id used internally to identify the tracking
     */
    public Long id;

    /**
     * User defined name for the tracking
     */
    public String name;

    /**
     * Courier id
     */
    public String trackingId;

    /**
     * Datetime for when the tracking was created
     */
    public LocalDateTime created;

    public LocalDateTime updated;

    public TrackingStatus status;

    public Courier courier;

    public Set<TrackingEvent> history;

    public void activate() {
        this.status = TrackingStatus.ACTIVE;
    }

    public void archive() {
        this.status = TrackingStatus.ARCHIVED;
    }
}
