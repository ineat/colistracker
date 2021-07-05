package com.ineat.colistracker.domain.usecase.tracking;

import com.ineat.colistracker.domain.model.Tracking;
import com.ineat.colistracker.domain.model.TrackingEvent;
import com.ineat.colistracker.domain.repository.TrackingRepository;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class TrackingUpdateEventHandler {
    private final TrackingRepository trackingRepository;

    public Uni<Tracking> execute(Long trackingId, TrackingEvent trackingEvent) {
        return trackingRepository.getById(trackingId);
    }
}
