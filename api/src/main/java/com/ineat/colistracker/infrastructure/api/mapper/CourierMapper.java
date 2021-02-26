package com.ineat.colistracker.infrastructure.api.mapper;

import com.ineat.colistracker.domain.model.Courier;
import com.ineat.colistracker.infrastructure.api.dto.CourierDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface CourierMapper {
    default CourierDTO toDTO(Courier courier) {
        return CourierDTO.builder()
                .id(courier.name())
                .name(courier.getName())
                .active(courier.isActive())
                .build();
    }
}
