package com.ineat.colistracker.infrastructure.exchange.consumer;

import com.ineat.colistracker.domain.model.TrackingEvent;
import com.ineat.colistracker.domain.usecase.tracking.TrackingUpdateEventHandler;
import com.ineat.colistracker.infrastructure.database.mapper.TrackingEventMapper;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.kafka.Record;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@RequiredArgsConstructor
public class TrackingEventConsumer {

    private final TrackingEventMapper trackingEventMapper;
    private final TrackingUpdateEventHandler trackingUpdateEventHandler;

    @Incoming("tracking-events")
    public Uni<Void> consume(Record<String, TrackingEvent> trackingUpdateEvent) {
        final Long trackingId = Long.parseLong(trackingUpdateEvent.key());
        return Uni.createFrom().nullItem();
    }
}
