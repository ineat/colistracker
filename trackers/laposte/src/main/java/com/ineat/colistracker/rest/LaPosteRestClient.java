package com.ineat.colistracker.rest;

import com.ineat.colistracker.config.RequestOkapiKeyHeaderFactory;
import com.ineat.colistracker.dto.LaPosteTrackingDTO;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/suivi/v2")
@RegisterRestClient(configKey = "laposte-api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterClientHeaders(RequestOkapiKeyHeaderFactory.class)
public interface LaPosteRestClient {
    @GET
    @Path("/idships/{id}")
    Uni<LaPosteTrackingDTO> getTracking(@PathParam("id") String trackingId);
}
