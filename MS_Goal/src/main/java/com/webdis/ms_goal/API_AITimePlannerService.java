package com.webdis.ms_goal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.springframework.http.HttpHeaders;
import java.util.List;

@Service
public class API_AITimePlannerService {
    private static final String OPENROUTER_API_URL = "https://openrouter.ai/api/v1/chat/completions";
    private static final String OPENROUTER_API_KEY = "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";
    //private static final String OPENAI_API_URL = "http://localhost:11434/api/generate"; // Exemple avec Ollama local
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private IGoalService iGoalService;

    public AiTimePlanResponse generatePlan(Goal goal, AIPlanningRequest request) {
            String prompt = buildPrompt(goal, request);


            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + OPENROUTER_API_KEY);

            // OpenRouter uses OpenAI-style chat structure
            String requestBody = """
    {
      "model": "mistralai/mistral-7b-instruct",
      "messages": [
        {"role": "system", "content": "You are a helpful assistant that builds realistic time management plans."},
        {"role": "user", "content": "%s"}
      ]
    }
    """.formatted(prompt);

            HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> response = restTemplate.postForEntity(OPENROUTER_API_URL, httpEntity, String.class);

            try {
                JsonNode json = mapper.readTree(response.getBody());
                String content = json.at("/choices/0/message/content").asText();

                AiTimePlanResponse plan = new AiTimePlanResponse();
                plan.setSummary("AI Plan");
                plan.setDailyPlan(List.of(content.split("\\n")));
                return plan;
            } catch (Exception e) {
                throw new RuntimeException("Erreur IA: " + e.getMessage());
            }
        }



    private String buildPrompt(Goal goal, AIPlanningRequest request) {
        String goalName = goal.getGoalName() != null ? goal.getGoalName() : "Objectif sans nom";
        String constraints = request.getConstraints() != null ? request.getConstraints() : "Aucune contrainte";
        String targetDate = goal.getTargetTime() != null ? goal.getTargetTime().toLocalDate().toString() : "date non précisée";
        int hours = request.getEstimatedDurationInHours();

        return "Planifie l'objectif \"" + goalName + "\" de " + hours + " heures à répartir avant le " + targetDate +
                ", en tenant compte des contraintes suivantes : " + constraints +
                ". Fournis un planning structuré avec des dates, heures, tâches et durée pour chaque bloc.";
    }



}

