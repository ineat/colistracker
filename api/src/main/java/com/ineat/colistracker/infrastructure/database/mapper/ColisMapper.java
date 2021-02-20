package com.ineat.colistracker.infrastructure.database.mapper;

import com.ineat.colistracker.domain.model.Colis;
import com.ineat.colistracker.infrastructure.database.entity.ColisEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface ColisMapper {

    Colis toDomain(ColisEntity entity);

    ColisEntity toEntity(Colis colis);

}
