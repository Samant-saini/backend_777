package com.example.project.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public GeminiService() {
        this.webClient = WebClient.builder().build();
        this.objectMapper = new ObjectMapper();
    }

    public String getFirstAidAdvice(String animalType, String symptoms) {

        // Build the prompt
        String prompt = String.format(
            "You are an animal first aid assistant. " +
            "A person found an injured %s with the following symptoms: %s. " +
            "Give clear, simple, numbered step-by-step first aid instructions " +
            "that anyone can follow without vet training. " +
            "Keep each step short and actionable. " +
            "End with: 'Take the animal to a vet as soon as possible.'",
            animalType, symptoms
        );

        // Build request body as JSON
        ObjectNode requestBody = objectMapper.createObjectNode();
        ArrayNode contentsArray = objectMapper.createArrayNode();
        ObjectNode contentObject = objectMapper.createObjectNode();
        ArrayNode partsArray = objectMapper.createArrayNode();
        ObjectNode partObject = objectMapper.createObjectNode();

        partObject.put("text", prompt);
        partsArray.add(partObject);
        contentObject.set("parts", partsArray);
        contentsArray.add(contentObject);
        requestBody.set("contents", contentsArray);

        try {
            // Call Gemini API
            String responseBody = webClient.post()
                    .uri(apiUrl + "?key=" + apiKey)
                    .header("Content-Type", "application/json")
                    .bodyValue(requestBody.toString())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            // Extract the text from response
            JsonNode root = objectMapper.readTree(responseBody);
            return root
                .path("candidates").get(0)
                .path("content")
                .path("parts").get(0)
                .path("text")
                .asText("Could not generate advice. Please contact a vet immediately.");

        } catch (Exception e) {
            return "AI service unavailable. Please contact a vet immediately.";
        }
    }
}
