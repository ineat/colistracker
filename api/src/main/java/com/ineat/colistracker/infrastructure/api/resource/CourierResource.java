package com.ineat.colistracker.infrastructure.api.resource;

import com.ineat.colistracker.domain.usecase.courier.CourierFetcher;
import com.ineat.colistracker.infrastructure.api.dto.CourierDTO;
import com.ineat.colistracker.infrastructure.api.mapper.CourierMapper;
import io.smallrye.mutiny.Multi;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.tags.Tags;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/couriers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tags(refs = {"couriers"})
@RequiredArgsConstructor
public class CourierResource {

    private final CourierFetcher courierFetcher;
    private final CourierMapper courierMapper;

    @GET
    public Multi<CourierDTO> all() {
        return courierFetcher.all()
                .onItem().transform(courierMapper::toDTO);
    }
}
