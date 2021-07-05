package com.ineat.colistracker.infrastructure.api.dto;

import com.ineat.colistracker.domain.model.Courier;
import com.ineat.colistracker.domain.model.TrackingStatus;

public class UpdateTrackingDTO {
    public String name;
    public String trackingId;
    public Courier courier;
    public TrackingStatus status;
}
