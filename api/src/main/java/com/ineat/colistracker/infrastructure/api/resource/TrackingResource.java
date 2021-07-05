package com.ineat.colistracker.infrastructure.api.resource;

import com.ineat.colistracker.domain.model.Tracking;
import com.ineat.colistracker.domain.usecase.tracking.TrackingAppender;
import com.ineat.colistracker.domain.usecase.tracking.TrackingFetcher;
import com.ineat.colistracker.domain.usecase.tracking.TrackingPatcher;
import com.ineat.colistracker.domain.usecase.tracking.TrackingRemover;
import com.ineat.colistracker.infrastructure.api.dto.CreateTrackingDTO;
import com.ineat.colistracker.infrastructure.api.dto.ErrorDTO;
import com.ineat.colistracker.infrastructure.api.dto.TrackingDTO;
import com.ineat.colistracker.infrastructure.api.dto.UpdateTrackingDTO;
import com.ineat.colistracker.infrastructure.api.mapper.TrackingMapper;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.jboss.resteasy.reactive.RestSseElementType;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/api/trackings")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tags(refs = {"trackings"})
@RequiredArgsConstructor
public class TrackingResource {

    private final TrackingPatcher trackingPatcher;
    private final TrackingAppender trackingAppender;
    private final TrackingFetcher trackingFetcher;
    private final TrackingRemover trackingRemover;
    private final TrackingMapper trackingMapper;

    @Inject
    @Channel("tracking-updates")
    Multi<Tracking> trackingUpdates;

    @POST
    public Uni<Response> create(CreateTrackingDTO body) {
        final Tracking tracking = trackingMapper.toDomain(body);

        return trackingAppender.execute(tracking)
                .onItem().transform(trackingMapper::toDTO)
                .onItem().transform(created -> Response.status(Response.Status.CREATED).entity(created).build())
                .onFailure().recoverWithItem(throwable -> {
                    final ErrorDTO errorDTO = ErrorDTO.builder().error(throwable.getCause().toString()).description(throwable.getMessage()).build();
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(errorDTO).build();
                });
    }

    @GET
    public Multi<TrackingDTO> findAll() {
        return trackingFetcher.findAll()
                .onItem().transform(trackingMapper::toDTO);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> delete(@PathParam("id") Long id) {
        return trackingRemover.execute(id)
                .onItem().transform(res -> Response.ok().build());
    }

    @GET
    @Path("/{id}")
    public Uni<TrackingDTO> get(@PathParam("id") Long id) {
        return trackingFetcher.getById(id)
                .onItem().transform(trackingMapper::toDTO);
    }

    @PATCH
    @Path("/{id}")
    public Uni<TrackingDTO> update(@PathParam("id") Long id, UpdateTrackingDTO dto) {
        final Tracking tracking = trackingMapper.toDomain(dto);
        tracking.id = id;

        return trackingPatcher.execute(tracking)
                .onItem().transform(trackingMapper::toDTO);
    }

    @GET
    @Path("/{id}/updates")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @RestSseElementType(MediaType.APPLICATION_JSON)
    public Multi<TrackingDTO> streamUpdates(@PathParam("id") Long id) {
        return trackingUpdates
                .filter(tracking -> tracking.id.equals(id))
                .onItem().transform(trackingMapper::toDTO);
    }
}
