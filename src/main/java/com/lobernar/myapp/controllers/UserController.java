package com.lobernar.myapp.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.lobernar.myapp.entities.User;
import com.lobernar.myapp.repositories.UserRepository;


import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/* 
    Retrieves user details based on the authenticated token. 
*/

@RestController
@RequestMapping("/api/users/")
public class UserController {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public UserController(final UserRepository userRepo, final PasswordEncoder pe){
        this.userRepo = userRepo;
        this.passwordEncoder = pe;
    }

    @GetMapping("me/")
    public ResponseEntity<User> getUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = userRepo.findByUsername(userDetails.getUsername()).orElseThrow(
            () -> new UsernameNotFoundException("User not found"));
        return ResponseEntity.ok(user);
    }

    @PutMapping("me/edit")
    public ResponseEntity<User> updateUser(@RequestBody Map<String, String> body){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User userToUpdate = userRepo.findByUsername(userDetails.getUsername()).orElseThrow(
            () -> new UsernameNotFoundException("User not found"));
        
        // Update fields if present in the map
        if(body.containsKey("firstName")) userToUpdate.setFirstName(body.get("firstName"));
        if(body.containsKey("lastName")) userToUpdate.setLastName(body.get("lastName"));
        if(body.containsKey("email")) userToUpdate.setEmail(body.get("email"));
        if(body.containsKey("username")) userToUpdate.setUsername(body.get("username"));
        if(body.containsKey("password")) {
            String encodedPassword = passwordEncoder.encode(body.get("password"));
            userToUpdate.setPassword(encodedPassword);
        }
        
        User updatedUser = userRepo.save(userToUpdate);
        return ResponseEntity.ok(userRepo.save(updatedUser));
    }

    @DeleteMapping("me/delete")
    public ResponseEntity<?> deleteUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User userToDelete = userRepo.findByUsername(userDetails.getUsername()).orElseThrow(
            () -> new UsernameNotFoundException("User not found"));
            
        this.userRepo.delete(userToDelete);
        return ResponseEntity.ok().build();
    }
}
