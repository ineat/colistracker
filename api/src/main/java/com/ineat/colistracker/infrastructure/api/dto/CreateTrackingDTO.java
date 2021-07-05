package com.ineat.colistracker.infrastructure.api.dto;

import com.ineat.colistracker.domain.model.Courier;

public class CreateTrackingDTO {
    public String name;
    public String trackingId;
    public Courier courier;
}
