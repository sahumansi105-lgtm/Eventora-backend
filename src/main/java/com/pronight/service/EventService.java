package com.pronight.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pronight.entity.EventEntity;
import com.pronight.repository.EventRepo;


@Service
public class EventService {

    @Autowired
    private EventRepo eventRepository;

    public EventEntity createEvent(EventEntity event) {
        return eventRepository.save(event);
    }

    public List<EventEntity> getAllEvents() {
        return getUpcomingEvents();
    }

    public EventEntity getEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    public EventEntity updateEvent(Long eventId, EventEntity eventData) {
        EventEntity event = getEventById(eventId);

        event.setTitle(eventData.getTitle());
        event.setDescription(eventData.getDescription());
        event.setCategory(eventData.getCategory());
        event.setSeason(eventData.getSeason());
        event.setMood(eventData.getMood());
        event.setCity(eventData.getCity());
        event.setVenue(eventData.getVenue());
        event.setAddress(eventData.getAddress());
        event.setEventDate(eventData.getEventDate());
        event.setEventTime(eventData.getEventTime());
        event.setGateOpenTime(eventData.getGateOpenTime());
        event.setEventEndTime(eventData.getEventEndTime());
        event.setImageUrl(eventData.getImageUrl());
        event.setArtistName(eventData.getArtistName());
        event.setOrganizerName(eventData.getOrganizerName());
        event.setMinAge(eventData.getMinAge());
        event.setDressCode(eventData.getDressCode());
        event.setStatus(eventData.getStatus());
        event.setFeatured(eventData.getFeatured());
        event.setTrending(eventData.getTrending());
        event.setTags(eventData.getTags());

        return eventRepository.save(event);
    }

    public String deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
        return "Event deleted successfully";
    }

    public List<EventEntity> getUpcomingEvents() {
        return eventRepository.findByEventDateGreaterThanEqualOrderByEventDateAscEventTimeAsc(LocalDate.now());
    }

    public List<EventEntity> getEventsBySeason(String season) {
        return eventRepository.findBySeasonIgnoreCase(season);
    }

    public List<EventEntity> getEventsByCity(String city) {
        return eventRepository.findByCityIgnoreCase(city);
    }

    public List<EventEntity> getEventsByCategory(String category) {
        return eventRepository.findByCategoryIgnoreCase(category);
    }

    public List<EventEntity> getEventsByMood(String mood) {
        return eventRepository.findByMoodIgnoreCase(mood);
    }

    public List<EventEntity> getFeaturedEvents() {
        return eventRepository.findByFeaturedTrue();
    }

    public List<EventEntity> getTrendingEvents() {
        return eventRepository.findByTrendingTrue();
    }

    public List<EventEntity> getEventsByStatus(String status) {
        return eventRepository.findByStatus(status.toUpperCase());
    }

    public List<EventEntity> searchEvents(String keyword) {
        return eventRepository.findByTitleContainingIgnoreCase(keyword);
    }
}
