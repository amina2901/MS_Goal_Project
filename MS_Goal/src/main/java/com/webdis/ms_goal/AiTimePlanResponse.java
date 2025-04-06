package com.webdis.ms_goal;

import lombok.Data;

import java.util.List;

@Data
public class AiTimePlanResponse {
    private String summary;
    private List<String> dailyPlan;
}

