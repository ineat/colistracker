package com.ineat.colistracker.domain.service;

import com.ineat.colistracker.domain.model.Colis;
import com.ineat.colistracker.domain.repository.ColisRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@Slf4j
public class ColisService {

    @Inject
    ColisRepository colisRepository;

    public Uni<Colis> create(Colis colis) {
        return colisRepository
                .create(colis)
                .onItem().invoke(created -> log.info("Created colis with id {} and tracking id {}", created.id, created.trackingId));
    }

    public Multi<Colis> findAll() {
        return colisRepository.all();
    }

    public Uni<Boolean> delete(Long id) {
        return colisRepository
                .delete(id)
                .onItem().invoke(aBoolean -> log.info("Colis with id {} deleted : {}", id, aBoolean));
    }

    public Uni<Colis> update(Colis colis) {
        return colisRepository
                .update(colis)
                .onItem().invoke(updated -> log.info("Colis with id {} successfully updated", updated.id));
    }
}
