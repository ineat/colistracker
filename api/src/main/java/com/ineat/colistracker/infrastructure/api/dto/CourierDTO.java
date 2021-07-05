package com.ineat.colistracker.infrastructure.api.dto;

import lombok.Builder;

@Builder
public class CourierDTO {
    public String id;
    public String name;
    public boolean active;
}
