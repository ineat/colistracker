package com.ineat.colistracker.domain.usecase.tracking;

import com.ineat.colistracker.domain.model.Tracking;
import com.ineat.colistracker.domain.repository.TrackingRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class TrackingFetcher {
    private final TrackingRepository trackingRepository;

    @Transactional
    public Multi<Tracking> findAll() {
        return trackingRepository.all();
    }

    public Uni<Tracking> getById(Long id) {
        return trackingRepository.getById(id);
    }
}
