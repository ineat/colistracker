package com.ineat.colistracker.infrastructure.api.resource;

import com.ineat.colistracker.domain.model.Colis;
import com.ineat.colistracker.domain.service.ColisService;
import com.ineat.colistracker.infrastructure.api.dto.ColisDTO;
import com.ineat.colistracker.infrastructure.api.dto.CreateOrUpdateColisDTO;
import com.ineat.colistracker.infrastructure.api.mapper.ColisMapper;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/api/colis")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ColisResource {

    @Inject
    ColisService colisService;

    @Inject
    ColisMapper colisMapper;

    @POST
    public Uni<Response> create(CreateOrUpdateColisDTO body) {
        final Colis colis = colisMapper.toDomain(body);

        return colisService.create(colis)
                .onItem().transform(created -> colisMapper.toDTO(created))
                .onItem().transform(created -> Response.status(Response.Status.CREATED).entity(created).build());
    }

    @GET
    public Multi<ColisDTO> findAll() {
        return colisService.findAll()
                .onItem().transform(colis -> colisMapper.toDTO(colis));
    }

    @DELETE
    @Path("/{id}")
    public Uni<Response> delete(@PathParam("id") Long id) {
        return colisService.delete(id)
                .onItem().transform(res -> Response.ok().build());
    }

    @PUT
    @Path("/{id}")
    public Uni<ColisDTO> update(@PathParam("id") Long id, CreateOrUpdateColisDTO dto) {
        final Colis colis = colisMapper.toDomain(dto);
        colis.id = id;

        return colisService.update(colis)
                .onItem().transform(updated -> colisMapper.toDTO(updated));
    }
}
