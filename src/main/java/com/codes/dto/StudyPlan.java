package com.codes.dto;

import java.util.List;

public record StudyPlan(
        String planName,
        Integer numberOfDays,
        Integer hoursPerDay,
        List<DayPlan> dayPlan){ }
