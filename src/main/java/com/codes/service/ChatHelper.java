package com.codes.service;

import com.codes.dto.StudyPlan;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiStreamingChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.TokenStream;
import org.jspecify.annotations.NonNull;

public class ChatHelper {
    static BotAssistant assistant;
    static {
        assistant = AiServices.builder(BotAssistant.class)
                .streamingChatModel(getChatModel())
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                .build();
    }
    public static TokenStream getResponse(String convoId, String input) {
//        UserMessage from = UserMessage.from(input);

//        UserMessage from = UserMessage.from(
//                TextContent.from(input),
//                ImageContent.from("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQi8qgnazPRLdr-bJ3gjlI4r5X2ymPSs_iHUWs8VzN1Gq8gnJxIZG8yqBYNZA0WC6CAk2Dy3zaaPeean4DxJ_Q2jJYgCO1y3-x2Riz8QA&s=10")
//        );

        return assistant.chat(convoId, input);
    }

    public static StudyPlan prepareStudyPlan(String input) {
        StreamingChatModel chatModel = getChatModel();
        StudyPlanner studyPlanner = AiServices.create(StudyPlanner.class, chatModel);
        return studyPlanner.getStudyPlan(input);
    }

    private static @NonNull StreamingChatModel getChatModel() {
        return GoogleAiGeminiStreamingChatModel.builder()
                .apiKey(
                        System.getenv("GOOGLE_AI_GEMINI_API_KEY")
                )
                .modelName("gemini-3.5-flash")
                .temperature(0.7)
                .build();
    }
}
