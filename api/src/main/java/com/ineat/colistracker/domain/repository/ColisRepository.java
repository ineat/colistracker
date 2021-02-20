package org.acme.domain.repository;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.acme.domain.model.Colis;

public interface ColisRepository {
    Uni<Void> create(Colis colis);

    Multi<Colis> all();
}
