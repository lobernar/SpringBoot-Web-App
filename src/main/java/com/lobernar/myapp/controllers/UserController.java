package com.lobernar.myapp.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.lobernar.myapp.entities.User;
import com.lobernar.myapp.repositories.UserRepository;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;

/* 
    Retrieves user details based on the authenticated token. 
*/

@RestController
@RequestMapping("/api/users/")
public class UserController {
    private final UserRepository userRepo;

    public UserController(final UserRepository userRepo){
        this.userRepo = userRepo;
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
}
