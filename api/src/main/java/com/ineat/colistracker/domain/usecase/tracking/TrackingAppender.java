package com.ineat.colistracker.domain.usecase.tracking;

import com.ineat.colistracker.domain.model.Tracking;
import com.ineat.colistracker.domain.repository.TrackingRepository;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class TrackingAppender {

    private final TrackingRepository trackingRepository;

    @Transactional
    public Uni<Tracking> execute(Tracking tracking) {
        tracking.activate();

        return trackingRepository
                .create(tracking)
                .onItem().invoke(created -> log.info("Created tracking with id {} and tracking id {}", created.id, created.trackingId));
    }
}
