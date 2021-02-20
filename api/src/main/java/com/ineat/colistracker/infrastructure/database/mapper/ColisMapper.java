package org.acme.infrastructure.database.mapper;

import org.acme.domain.model.Colis;
import org.acme.infrastructure.database.entity.ColisEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface ColisMapper {

    Colis toDomain(ColisEntity entity);

    ColisEntity toEntity(Colis colis);

}
