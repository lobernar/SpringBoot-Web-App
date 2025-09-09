package com.lobernar.myapp.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.lobernar.myapp.entities.User;
import com.lobernar.myapp.repositories.AuthRepository;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/users/")
public class UserController {
    private final AuthRepository authRepo;

    public UserController(final AuthRepository authRepo){
        this.authRepo = authRepo;
    }

    @GetMapping("me/{username}")
    public User getUser(@PathVariable("username") String username){
        System.out.println("Username: " + username);
        return authRepo.findByUsername(username).get();
    }
}
