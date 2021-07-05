package com.ineat.colistracker.domain.usecase.tracking;

import com.ineat.colistracker.domain.repository.TrackingRepository;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class TrackingRemover {
    private final TrackingRepository trackingRepository;

    @Transactional
    public Uni<Boolean> execute(Long id) {
        return trackingRepository
                .delete(id)
                .onItem().invoke(aBoolean -> log.info("Colis with id {} deleted : {}", id, aBoolean));
    }
}
