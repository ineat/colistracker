package com.ineat.colistracker.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public class ShipmentDTO {
    public String holder;
    public String idShip;
    public String product;
    public Boolean isFinal;
    public LocalDateTime entryDate;
    public List<EventDTO> event;
    public List<TimelineEventDTO> timeline;
    public ContextDataDTO contextData;
}
