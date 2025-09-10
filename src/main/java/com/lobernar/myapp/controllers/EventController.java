package com.lobernar.myapp.controllers;

import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lobernar.myapp.repositories.EventRepository;
import com.lobernar.myapp.entities.Event;

@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventRepository eventRepo;

    public EventController(final EventRepository eventRepo) {
        this.eventRepo = eventRepo;
    }

    @GetMapping("/{id}")
    public Event getEvent(@PathVariable("id") Integer id){
        Optional<Event> optEvent = this.eventRepo.findById(id);
        if(!optEvent.isPresent()) {return null;}
        Event event = optEvent.get();
        return event;
    }
}
