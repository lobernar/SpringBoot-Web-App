package com.lobernar.myapp.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.lobernar.myapp.entities.Event;
import com.lobernar.myapp.entities.EventDTO;
import com.lobernar.myapp.entities.User;

/**
 * Repository interface for event-related database operations.
* Extends JpaRepository to provide CRUD operations for Event entities.
* Add custom query methods as needed.
*/

public interface EventRepository extends CrudRepository<Event, Integer>{
    Optional<Event> findByName(String name);
    List<Event> findByOwner(User user);
    @Query("SELECT new com.lobernar.myapp.entities.EventDTO(" +
       "e.id, e.name, e.owner.username, e.startDate, e.endDate) " +
       "FROM Event e WHERE e.owner = :owner")
    List<EventDTO> findDTOsByOwner(@Param("owner") User owner);
}
