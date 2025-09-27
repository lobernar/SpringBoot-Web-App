package com.lobernar.myapp.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.lobernar.myapp.entities.User;
import com.lobernar.myapp.repositories.UserRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("me/{username}")
    public User getUser(@PathVariable("username") String username){
        return userRepo.findByUsername(username).get();
    }
}
