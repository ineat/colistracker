package com.ineat.colistracker.infrastructure.exchange.mapper;

import com.ineat.colistracker.domain.model.TrackingEvent;
import com.ineat.colistracker.infrastructure.exchange.avro.TrackingUpdateEvent;

import org.mapstruct.Mapper;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Mapper
public interface TrackingEventMapper {
    TrackingEvent toDomain(TrackingUpdateEvent trackingUpdateEvent);

    default LocalDateTime toLocalTime(Long timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC);
    }
}
