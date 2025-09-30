package com.lobernar.myapp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.lobernar.myapp.entities.Event;

public interface EventRepository extends CrudRepository<Event, Integer>{
    Optional<Event> findByName(String name);
    List<Event> findByOwner(String username);
}
