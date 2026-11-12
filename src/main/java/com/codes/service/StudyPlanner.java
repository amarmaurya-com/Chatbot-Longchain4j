package com.codes.service;

import com.codes.dto.StudyPlan;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface StudyPlanner {
    @UserMessage("You are an expert in planning the study plan for student.\n" +
            "Create a useful plan for giving topic {{it}}")
    StudyPlan getStudyPlan(@V("it") String topic);
}
