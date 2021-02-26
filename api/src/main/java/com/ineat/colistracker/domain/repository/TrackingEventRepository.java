package com.ineat.colistracker.domain.repository;

import com.ineat.colistracker.domain.model.TrackingEvent;
import io.smallrye.mutiny.Uni;

public interface TrackingEventRepository {
    Uni<TrackingEvent> save(TrackingEvent trackingEvent);
}
