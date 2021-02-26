package com.ineat.colistracker.domain.usecase.courier;

import com.ineat.colistracker.domain.model.Courier;
import io.smallrye.mutiny.Multi;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CourierFetcher {

    public Multi<Courier> all() {
        return Multi.createFrom().items(Courier.values());
    }
}
