package com.myntra.hackathon.models.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeSlot {
    private Long startTime;
    private Long endTime;
    private String date;
}
