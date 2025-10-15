package com.lobernar.myapp.service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.lobernar.myapp.entities.User;
import com.lobernar.myapp.repositories.UserRepository;

/**
 * Service class for user-related business logic and data access.
 * Handles authentication checks, user CRUD operations, and password encoding.
 */

@Service
public class UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder passEncoder;

    /**
     * Constructor for UserService. Injects UserRepository and PasswordEncoder.
     * @param ur UserRepository instance
     * @param pe PasswordEncoder instance
     */
    @Autowired
    public UserService(UserRepository ur, PasswordEncoder pe){
        this.userRepo = ur;
        this.passEncoder = pe;
    }

    /**
     * Checks if the current user is authenticated.
     * @return Authentication object if authenticated, null otherwise
     */
    private Authentication checkAuthenticated(){
        // Checks if user is authenticated
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null && auth.isAuthenticated()){
            return auth;
        }
        return null;
    }

    /**
     * Finds a user by their username.
     * @param username the username to search for
     * @return an Optional containing the User if found, or empty otherwise
     */
    public Optional<User> findByUsername(String username){
        return this.userRepo.findByUsername(username);
    }

    /**
     * Retrieves the currently authenticated user's details.
     * @return User object if authenticated, null otherwise
     */
    public User getUser(){
        Authentication auth = checkAuthenticated();
        if(auth == null){return null;}
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = userRepo.findByUsername(userDetails.getUsername()).orElseThrow(
            () -> new UsernameNotFoundException("User not found"));
        return user;
    }

    /**
     * Adds a new user to the database, encoding their password.
     * @param user the user to add
     * @return the saved User entity
     */
    public User addUser(User user){
        user.setPassword(passEncoder.encode(user.getPassword()));
        return this.userRepo.save(user);
    }

    /**
     * Updates the currently authenticated user's information with provided fields.
     * @param body Map containing fields to update
     * @return the updated User entity, or null if not authenticated
     */
    public User updateUser(@RequestBody Map<String, String> body){
        Authentication auth = checkAuthenticated();
        if(auth == null){return null;}

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User userToUpdate = userRepo.findByUsername(userDetails.getUsername()).orElseThrow(
            () -> new UsernameNotFoundException("User not found"));
        // Update fields if present in the map
        if(body.containsKey("firstName")) userToUpdate.setFirstName(body.get("firstName"));
        if(body.containsKey("lastName")) userToUpdate.setLastName(body.get("lastName"));
        if(body.containsKey("email")) userToUpdate.setEmail(body.get("email"));
        if(body.containsKey("username")) userToUpdate.setUsername(body.get("username"));
        if(body.containsKey("password")) {
            String encodedPassword = this.passEncoder.encode(body.get("password"));
            userToUpdate.setPassword(encodedPassword);
        }
        return this.userRepo.save(userToUpdate);
    }

    /**
     * Deletes the currently authenticated user.
     * @param username Username of the user to delete (not used, uses authenticated user)
     * @return the deleted User entity, or null if not authenticated
     */
    public User deleteUser(String username){
        Authentication auth = checkAuthenticated();
        if(auth == null){return null;}

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User userToDelete = userRepo.findByUsername(userDetails.getUsername()).orElseThrow(
            () -> new UsernameNotFoundException("User not found"));
        
        this.userRepo.delete(userToDelete);
        return userToDelete;
    }
    
}
