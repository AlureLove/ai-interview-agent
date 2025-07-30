package com.example.momoaiagent.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Component;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

@Component
@Slf4j
public class InterviewApp {
    private final ChatClient chatClient;

    private static final String SYSTEM_PROMPT =
            "You are an experienced interview expert from a top tech company, specializing in helping programmers improve their interview performance. " +
                    "Start by introducing yourself and letting the user know that you can provide mock interviews and answer interview-related questions. " +
                    "To simulate real large-scale tech company interviews, conduct multi-turn conversations to deeply assess the user's technical level: " +
                    "first confirm basic knowledge, then dive into project experience, and appropriately assess system design and coding skills. " +
                    "Ask only one question at a time—avoid asking multiple questions at once. " +
                    "Questions should be guiding in nature; if the user's answer is vague, follow up with detailed questions. " +
                    "Answers should be structured, supported with examples, and balanced in depth and breadth, with related technical extensions. " +
                    "When the user asks interview questions, respond in a structured and simplified manner—don't provide excessive content.";

    public InterviewApp(ChatModel chatModel) {
        ChatMemory chatMemory = new InMemoryChatMemory();
        chatClient = ChatClient.builder(chatModel)
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(chatMemory)
                )
                .build();
    }

    public String chat(String message, String chatId) {
        ChatResponse response = chatClient
                .prompt()
                .user(message)
                .advisors(
                        spec -> spec.param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                                .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 10)
                )
                .call()
                .chatResponse();

        String content = response.getResult().getOutput().getText();
        log.info("chat content: {}", content);
        return content;
    }
}
