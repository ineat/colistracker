package com.ineat.colistracker.infrastructure.api.dto;

import lombok.Builder;

@Builder
public class ErrorDTO {
    public String error;
    public String description;
}
