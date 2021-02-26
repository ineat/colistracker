package com.ineat.colistracker.domain.repository;

import com.ineat.colistracker.domain.model.Tracking;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface TrackingRepository {
    Uni<Tracking> create(Tracking tracking);

    Uni<Tracking> getById(Long id);

    Multi<Tracking> all();

    Uni<Boolean> delete(Long id);

    Uni<Tracking> update(Tracking tracking);
}
