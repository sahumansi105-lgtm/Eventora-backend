package com.pronight.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pronight.entity.EventEntity;
import com.pronight.entity.UserEntity;
import com.pronight.repository.EventRepo;
import com.pronight.repository.UserRepo;

@Service
public class RecommendationService {

    @Autowired
    private AiService aiService;

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private EventRepo eventRepository;

    public String getRecommendations(Long userId) {

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<EventEntity> events = eventRepository.findAll();

        StringBuilder eventList = new StringBuilder();

        for (EventEntity e : events) {
            eventList.append(e.getEventId())
                    .append(" - ")
                    .append(e.getTitle())
                    .append(" - ")
                    .append(e.getCategory())
                    .append(" - ")
                    .append(e.getCity())
                    .append("\n");
        }

        String prompt = """
        You are an AI event recommendation engine.

        User Interests:
        %s

        Available Events:
        %s

        Task:
        Recommend TOP 5 events.

        Return ONLY JSON:
        [
          {
            "eventId": 1,
            "title": "Event name",
            "reason": "Why recommended"
          }
        ]
        """.formatted(user.getInterests(), eventList);

        return aiService.getAIResponse(prompt);
    }
}