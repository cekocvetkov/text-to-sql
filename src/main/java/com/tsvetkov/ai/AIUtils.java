package com.tsvetkov.ai;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;

public class AIUtils {

    public static AIAssistant getAIAssistant() {
        return AiServices.builder(AIAssistant.class)
                .chatModel(initOllamaChatModel())
                .build();
    }

    private static ChatModel initOllamaChatModel()
    {
        return OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("llama3.2:1b")
                .build();
    }

}
