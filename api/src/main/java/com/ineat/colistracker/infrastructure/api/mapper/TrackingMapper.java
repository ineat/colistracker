package com.ineat.colistracker.infrastructure.api.mapper;

import com.ineat.colistracker.domain.model.Tracking;
import com.ineat.colistracker.domain.model.TrackingEvent;
import com.ineat.colistracker.infrastructure.api.dto.*;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "cdi")
public interface TrackingMapper {

    TrackingDTO toDTO(Tracking tracking);

    TrackingListDTO toListDTO(Tracking tracking);

    Set<TrackingEventDTO> toListDTOs(Set<TrackingEvent> events);

    Tracking toDomain(CreateTrackingDTO dto);

    Tracking toDomain(UpdateTrackingDTO dto);

}
