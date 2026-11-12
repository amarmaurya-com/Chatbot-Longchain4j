package com.codes.service;

import dev.langchain4j.service.*;

public interface BotAssistant {
    @SystemMessage(fromResource = "ai-system-prompt.txt")
//    @UserMessage("Today's date is 24/jan/21 {{it}}")
    TokenStream chat(@MemoryId String  memoryId, @UserMessage String message);
}
