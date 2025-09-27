package com.lobernar.myapp.repositories;

import org.springframework.data.repository.CrudRepository;

import com.lobernar.myapp.entities.Event;

public interface EventRepository extends CrudRepository<Event, Integer>{
}
