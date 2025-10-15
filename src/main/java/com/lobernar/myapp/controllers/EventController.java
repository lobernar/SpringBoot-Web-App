package com.lobernar.myapp.controllers;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
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
import com.lobernar.myapp.entities.EventDTO;
import com.lobernar.myapp.entities.User;
import com.lobernar.myapp.repositories.EventRepository;
import com.lobernar.myapp.repositories.UserRepository;


/**
 * Controller for handling event-related API endpoints.
 * Provides endpoints for creating, retrieving, updating, and deleting events.
 */

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventRepository eventRepo;
    private final UserRepository userRepo;

    /**
     * Constructs an EventController with the specified EventRepository and UserRepository.
     * @param eventRepo the repository for event operations
     * @param ur the repository for user operations
     */
    public EventController(final EventRepository eventRepo, final UserRepository ur) {
        this.eventRepo = eventRepo;
        this.userRepo = ur;
    }

    /**
     * Retrieves all events for the authenticated user.
     * @return a ResponseEntity containing a list of EventDTOs for the user
     */
    @GetMapping("/me")
    public ResponseEntity<List<EventDTO>> getEvent(){
        // Check if user is authenticated
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // Get all Events of an User
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String username = userDetails.getUsername();
        User user = userRepo.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(eventRepo.findDTOsByOwner(user));
    }
    
    /**
     * Adds a new event for the authenticated user based on the provided request body.
     * @param body a map containing event properties
     * @return a ResponseEntity containing the created EventDTO
     */
    @PostMapping("me/post")
    public ResponseEntity<EventDTO> addEvent(@RequestBody Map<String, String> body) {
        // Check if user is authenticated
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        
        // Look up the User entity by username
        String username = userDetails.getUsername();
        User user = userRepo.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));
        // Extract event properties from request
        String eventName = body.get("eventName");
        String eventStartString = body.get("eventStart");
        String eventEndString = body.get("eventEnd");

        // Convert String to ZonedDateTime
        // Parse the LocalDateTime first
        LocalDateTime startLocal = LocalDateTime.parse(eventStartString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        LocalDateTime endLocal = LocalDateTime.parse(eventEndString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        // Convert to ZonedDateTime
        ZonedDateTime eventStart = startLocal.atZone(ZoneOffset.UTC);
        ZonedDateTime eventEnd = endLocal.atZone(ZoneOffset.UTC);

        Event event = new Event(user, eventName, eventStart, eventEnd);
        event = this.eventRepo.save(event);

        // Build DTO manually to avoid lazy loading
        EventDTO dto = new EventDTO(event.getId(), 
                            event.getName(), 
                            user.getUsername(), 
                            event.getStartDate(), 
                            event.getEndDate());
                            
        return ResponseEntity.ok(dto);
    }
    
}
