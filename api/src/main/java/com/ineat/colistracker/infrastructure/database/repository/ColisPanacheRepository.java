package org.acme.infrastructure.database.repository;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.domain.model.Colis;
import org.acme.domain.repository.ColisRepository;
import org.acme.infrastructure.database.entity.ColisEntity;
import org.acme.infrastructure.database.mapper.ColisMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ColisPanacheRepository implements ColisRepository, PanacheRepository<ColisEntity> {

    @Inject
    ColisMapper mapper;

    @Override
    public Uni<Void> create(Colis colis) {
        final ColisEntity entity = mapper.toEntity(colis);
        return persist(entity);
    }

    @Override
    public Multi<Colis> all() {
        return findAll().stream()
                .onItem().transform(entity -> mapper.toDomain(entity));
    }
}
