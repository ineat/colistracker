package com.ineat.colistracker.infrastructure.database.repository;

import com.ineat.colistracker.domain.model.Tracking;
import com.ineat.colistracker.infrastructure.database.entity.TrackingEntity;
import com.ineat.colistracker.infrastructure.database.mapper.TrackingMapper;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import com.ineat.colistracker.domain.repository.TrackingRepository;
import lombok.RequiredArgsConstructor;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@RequiredArgsConstructor
public class TrackingPanacheRepository implements TrackingRepository, PanacheRepository<TrackingEntity> {

    private final TrackingMapper mapper;

    @Override
    public Uni<Tracking> create(Tracking tracking) {
        final TrackingEntity entity = mapper.toEntity(tracking);

        return persistAndFlush(entity)
                .onItem().transform(persisted -> mapper.toDomain(entity));
    }

    @Override
    public Uni<Tracking> getById(Long id) {
        return findById(id)
                .onItem().transform(mapper::toDomain);
    }

    @Override
    public Multi<Tracking> all() {
        return findAll()
                .stream()
                .onItem().transform(mapper::toDomain);
    }

    @Override
    public Uni<Boolean> delete(Long id) {
        return deleteById(id);
    }

    @Override
    public Uni<Tracking> update(Tracking tracking) {
        return Uni.createFrom().nullItem();
    }
}
