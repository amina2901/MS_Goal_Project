package com.webdis.ms_goal;

import lombok.Data;


@Data
public class AIPlanningRequest {
    private String constraints;
    private int estimatedDurationInHours;
}
