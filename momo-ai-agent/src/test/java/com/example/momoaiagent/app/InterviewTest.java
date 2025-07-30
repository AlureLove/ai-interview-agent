package com.example.momoaiagent.app;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class InterviewTest {
    @Resource
    private InterviewApp interviewApp;

    @Test
    void testChat() {
        String chatId = UUID.randomUUID().toString();

        String message = "Hello, I'm Jethro! I have one year of experience in Java.";
        String answer = interviewApp.chat(message, chatId);
        Assertions.assertNotNull(answer);

        message = "I wonder to know except MYSQL, what other databases are commonly used in Java?";
        answer = interviewApp.chat(message, chatId);
        Assertions.assertNotNull(answer);

        message = "Which database did I just mention? I told you a moment ago—please help me recall.";
        answer = interviewApp.chat(message, chatId);
        Assertions.assertNotNull(answer);
    }

    @Test
    void testDoChatWithRAG() {
        String chatId = UUID.randomUUID().toString();

        String message = "What is Java?";
        String answer = interviewApp.doChatWithRag(message, chatId);
        Assertions.assertNotNull(answer);
    }
}
