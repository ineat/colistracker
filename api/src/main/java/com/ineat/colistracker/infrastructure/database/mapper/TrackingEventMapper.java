package com.ineat.colistracker.infrastructure.database.mapper;

import com.ineat.colistracker.domain.model.TrackingEvent;
import com.ineat.colistracker.infrastructure.database.entity.TrackingEventEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface TrackingEventMapper {

    TrackingEventEntity toEntity(TrackingEvent trackingEvent);

    TrackingEvent toDomain(TrackingEventEntity trackingEventEntity);

}
