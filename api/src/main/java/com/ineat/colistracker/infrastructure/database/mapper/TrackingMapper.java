package com.ineat.colistracker.infrastructure.database.mapper;

import com.ineat.colistracker.domain.model.Tracking;
import com.ineat.colistracker.infrastructure.database.entity.TrackingEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi", uses = TrackingEventMapper.class)
public interface TrackingMapper {

    Tracking toDomain(TrackingEntity entity);

    TrackingEntity toEntity(Tracking tracking);

}
