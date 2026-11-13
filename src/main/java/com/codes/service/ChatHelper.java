
package com.codes.service;

import com.codes.dto.StudyPlan;
import com.codes.tools.Calculater;
import com.codes.tools.FileWriteTool;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiStreamingChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.TokenStream;
import org.jspecify.annotations.NonNull;

public class ChatHelper {

    private static final BotAssistant assistant =
            AiServices.builder(BotAssistant.class)
                    .streamingChatModel(getChatModel())
                    .chatMemoryProvider(memoryId ->
                            MessageWindowChatMemory.builder()
                                    .id(memoryId)
                                    .maxMessages(10)
                                    .build()
                    )
                    .tools(new Calculater(),
                            new FileWriteTool()
                    )
                    .build();

    public static TokenStream getResponse(
            String convoId,
            String input
    ) {
        return assistant.chat(convoId, input);
    }

    private static @NonNull StreamingChatModel getChatModel() {

        String apiKey = System.getenv("GOOGLE_AI_GEMINI_API_KEY");

        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "GOOGLE_AI_GEMINI_API_KEY is missing"
            );
        }

        return GoogleAiGeminiStreamingChatModel.builder()
                .apiKey(apiKey)
                .modelName("gemini-3.5-flash")
                .temperature(0.7)
                .build();
    }
    public static StudyPlan prepareStudyPlan(String input) {
        StreamingChatModel chatModel = getChatModel();
        StudyPlanner studyPlanner = AiServices.create(StudyPlanner.class, chatModel);
        return studyPlanner.getStudyPlan(input);
    }
}
