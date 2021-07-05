package com.ineat.colistracker.rest;

import com.ineat.colistracker.dto.LaPosteTrackingDTO;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class LaPosteFetcher {
    @Inject
    @RestClient
    LaPosteRestClient laPosteRestClient;

    public Uni<LaPosteTrackingDTO> test(String trackingId) {
        return laPosteRestClient.getTracking(trackingId);
    }
}
