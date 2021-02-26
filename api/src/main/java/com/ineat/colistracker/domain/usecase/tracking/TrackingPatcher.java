package com.ineat.colistracker.domain.usecase.tracking;

import com.ineat.colistracker.domain.model.Tracking;
import com.ineat.colistracker.domain.repository.TrackingRepository;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
@Slf4j
@RequiredArgsConstructor
public class TrackingPatcher {

    private final TrackingRepository trackingRepository;

    @Transactional
    public Uni<Tracking> execute(Tracking tracking) {
        if (tracking.id == null) {
            return Uni.createFrom().failure(() -> new IllegalArgumentException("Tracking to update should not have null id"));
        }

        return trackingRepository.getById(tracking.id)
                .onFailure().invoke(error -> log.error("Could not find tracking with id {} to update", tracking.id, error))
                .onItem().transform(existing -> updateExistingTrackingWithFieldsToModify(tracking, existing))
                .onItem().call(trackingRepository::update)
                .onItem().invoke(updated -> log.info("Colis with id {} successfully updated", updated.id));
    }

    private Tracking updateExistingTrackingWithFieldsToModify(Tracking toModify, Tracking existing) {
        if (toModify.name != null) {
            existing.name = toModify.name;
        }

        if (toModify.trackingId != null) {
            existing.trackingId = toModify.trackingId;
        }

        if (toModify.status != null) {
            existing.status = toModify.status;
        }

        return existing;
    }
}
