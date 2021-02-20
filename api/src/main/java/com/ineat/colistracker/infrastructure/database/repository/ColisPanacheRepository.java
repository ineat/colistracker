package com.ineat.colistracker.infrastructure.database.repository;

import com.ineat.colistracker.domain.model.Colis;
import com.ineat.colistracker.infrastructure.database.entity.ColisEntity;
import com.ineat.colistracker.infrastructure.database.mapper.ColisMapper;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import com.ineat.colistracker.domain.repository.ColisRepository;
import org.hibernate.reactive.mutiny.Mutiny;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class ColisPanacheRepository implements ColisRepository, PanacheRepository<ColisEntity> {

    @Inject
    ColisMapper mapper;

    @Override
    public Uni<Colis> create(Colis colis) {
        final ColisEntity entity = mapper.toEntity(colis);

        return persistAndFlush(entity)
                .map(persisted -> mapper.toDomain(entity));
    }

    @Override
    public Multi<Colis> all() {
        return findAll().stream()
                .onItem().transform(entity -> mapper.toDomain(entity));
    }

    @Override
    public Uni<Boolean> delete(Long id) {
        return deleteById(id);
    }

    @Override
    public Uni<Colis> update(Colis colis) {
        return Uni.createFrom().nullItem();
    }
}
