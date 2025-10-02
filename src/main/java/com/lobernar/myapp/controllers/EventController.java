package com.lobernar.myapp.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lobernar.myapp.entities.Event;
import com.lobernar.myapp.repositories.EventRepository;


@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventRepository eventRepo;

    public EventController(final EventRepository eventRepo) {
        this.eventRepo = eventRepo;
    }

    @GetMapping("/me")
    public ResponseEntity<List<Event>> getEvent(){
        // Check if user is authenticated
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // Get all Events of an User
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();
        return ResponseEntity.ok(this.eventRepo.findByOwner(username));
    }
    
    @PostMapping("me/post")
    public ResponseEntity<Event> addEvent(@RequestBody Map<String, String> body) {
        // Check if user is authenticated
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        // Extract event properties from request
        String username = userDetails.getUsername();
        String eventName = body.get("eventName");
        String eventStartString = body.get("eventStart");
        String eventEndString = body.get("eventEnd");

        // Convert String to LocalDataTime
        LocalDateTime eventStart = LocalDateTime.parse(eventStartString, DateTimeFormatter.ISO_DATE_TIME);
        LocalDateTime eventEnd = LocalDateTime.parse(eventEndString, DateTimeFormatter.ISO_DATE_TIME);

        Event event = new Event(username, eventName, eventStart, eventEnd);

        return ResponseEntity.ok(this.eventRepo.save(event));
    }
    
}
