package com.lobernar.myapp.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

import com.lobernar.myapp.entities.User;


/**
 * Repository interface for user-related database operations.
 * Extends CrudRepository to provide CRUD operations for User entities.
 */

public interface UserRepository extends CrudRepository<User, Integer>{
    /**
     * Finds a user by their username.
     * @param username the username to search for
     * @return an Optional containing the User if found, or empty otherwise
     */
    Optional<User> findByUsername(String username);
}
