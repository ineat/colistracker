package org.acme.infrastructure.api.mapper;

import org.acme.domain.model.Colis;
import org.acme.infrastructure.api.dto.ColisDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface ColisMapper {
    Colis toDomain(ColisDTO dto);

    ColisDTO toEntity(Colis colis);

}
