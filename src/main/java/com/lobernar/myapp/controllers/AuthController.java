package com.lobernar.myapp.controllers;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lobernar.myapp.repositories.UserRepository;
import com.lobernar.myapp.entities.User;

@RestController
@RequestMapping("/api/auth")
public class AuthController{

    @Autowired
    private final UserRepository userRepo;

    public AuthController(final UserRepository userRepo){
        this.userRepo = userRepo;
    }

    @GetMapping("/users")
    public Iterable<User> getAllUsers(){
        Iterable<User> users = userRepo.findAll();
        for(User u : users){System.out.println(u.getUsername());}
        return users;
    }

    @PostMapping("/login")
    public Integer login(@RequestBody Map<String, String> body) {
        // Deserialize JSON
        String username = body.get("username");
        String password = body.get("password");
        Optional<User> optUser = this.userRepo.findByUsername(username);
        // Check if user exists in DB
        if(!optUser.isPresent()){return -1;}
        // Check if username-password match
        User user = optUser.get();
        if(user != null && user.getPassword().equals(password)){return user.getId();}
        else {return -1;}
    }

    @PostMapping("/signup")
    public Integer signup(@RequestBody Map<String, String> body){
        String username = body.get("username");
        String password = body.get("password");
        String firstName = body.get("firstName");
        String lastName = body.get("lastName");
        String email = body.get("email");
        
        User newUser = new User(username, password, firstName, lastName, email);
        // Check that no two users have the same username
        Optional<User> optUser = this.userRepo.findByUsername(body.get("username"));
        if(optUser.isPresent()) {return -1;}
        // Save new user to the DB
        this.userRepo.save(newUser);
        return newUser.getId();
    }
}