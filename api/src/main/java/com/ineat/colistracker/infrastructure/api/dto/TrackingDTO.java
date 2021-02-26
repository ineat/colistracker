package com.ineat.colistracker.infrastructure.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Set;

public class TrackingDTO {
    public Long id;
    public String name;
    public String trackingId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime created;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime updated;
    public Set<TrackingEventDTO> trackingHistory;
}
