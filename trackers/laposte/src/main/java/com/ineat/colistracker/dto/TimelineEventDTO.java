package com.ineat.colistracker.dto;

import java.time.LocalDateTime;

public class TimelineEventDTO {
    public Integer id;
    public String shortLabel;
    public String longLabel;
    public LocalDateTime localDateTime;
    public String country;
    public Boolean status;
    public Integer type;
}
