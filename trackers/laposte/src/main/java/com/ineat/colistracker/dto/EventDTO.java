package com.ineat.colistracker.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class EventDTO {
    public LocalDateTime date;
    public String label;
    public String code;
}
