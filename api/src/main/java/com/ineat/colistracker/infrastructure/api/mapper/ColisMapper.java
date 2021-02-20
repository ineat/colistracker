package com.ineat.colistracker.infrastructure.api.mapper;

import com.ineat.colistracker.domain.model.Colis;
import com.ineat.colistracker.infrastructure.api.dto.ColisDTO;
import com.ineat.colistracker.infrastructure.api.dto.CreateOrUpdateColisDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface ColisMapper {
    Colis toDomain(ColisDTO dto);

    ColisDTO toDTO(Colis colis);

    Colis toDomain(CreateOrUpdateColisDTO dto);
}
