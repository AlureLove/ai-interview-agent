package com.example.momoaiagent.rag;

import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class InterviewAppVectorStoreConfig {

    @Resource
    private AppDocumentReader appDocumentReader;

    @Bean
    public VectorStore interviewAppVectorStore(EmbeddingModel embeddingModel) {
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(embeddingModel).build();

        List<Document> documents = appDocumentReader.loadMarkdown();
        simpleVectorStore.add(documents);

        return simpleVectorStore;
    }
}
