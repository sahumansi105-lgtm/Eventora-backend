package com.pronight.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.pronight.entity.EventEntity;
import com.pronight.service.EventService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping
    public EventEntity createEvent(@RequestBody EventEntity event) {
    	System.out.println(event);
    	 System.out.println("Date = " + event.getEventDate());
    	    System.out.println("Time = " + event.getEventTime());
        return eventService.createEvent(event);
    }

    @GetMapping
    public List<EventEntity> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{eventId}")
    public EventEntity getEventById(@PathVariable Long eventId) {
        return eventService.getEventById(eventId);
    }

    @PutMapping("/{eventId}")
    public EventEntity updateEvent(@PathVariable Long eventId, @RequestBody EventEntity event) {
        return eventService.updateEvent(eventId, event);
    }

    @DeleteMapping("/{eventId}")
    public String deleteEvent(@PathVariable Long eventId) {
        return eventService.deleteEvent(eventId);
    }

    @GetMapping("/upcoming")
    public List<EventEntity> getUpcomingEvents() {
        return eventService.getUpcomingEvents();
    }

    @GetMapping("/season/{season}")
    public List<EventEntity> getEventsBySeason(@PathVariable String season) {
        return eventService.getEventsBySeason(season);
    }

    @GetMapping("/city/{city}")
    public List<EventEntity> getEventsByCity(@PathVariable String city) {
        return eventService.getEventsByCity(city);
    }

    @GetMapping("/category/{category}")
    public List<EventEntity> getEventsByCategory(@PathVariable String category) {
        return eventService.getEventsByCategory(category);
    }

    @GetMapping("/mood/{mood}")
    public List<EventEntity> getEventsByMood(@PathVariable String mood) {
        return eventService.getEventsByMood(mood);
    }

    @GetMapping("/featured")
    public List<EventEntity> getFeaturedEvents() {
        return eventService.getFeaturedEvents();
    }

    @GetMapping("/trending")
    public List<EventEntity> getTrendingEvents() {
        return eventService.getTrendingEvents();
    }

    @GetMapping("/status/{status}")
    public List<EventEntity> getEventsByStatus(@PathVariable String status) {
        return eventService.getEventsByStatus(status);
    }
    
    @GetMapping("/search")
    public List<EventEntity> searchEvents(
            @RequestParam String keyword) {

        return eventService.searchEvents(keyword);
    }
}