package com.ineat.colistracker.domain.repository;

import com.ineat.colistracker.domain.model.Colis;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface ColisRepository {
    Uni<Colis> create(Colis colis);

    Multi<Colis> all();

    Uni<Boolean> delete(Long id);

    Uni<Colis> update(Colis colis);
}
