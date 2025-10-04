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

@Service
public class UserService {
    
    private final UserRepository userRepo;
    private final PasswordEncoder passEncoder;

    @Autowired
    public UserService(UserRepository ur, PasswordEncoder pe){
        this.userRepo = ur;
        this.passEncoder = pe;
    }

    public Optional<User> findByUsername(String username){
        return this.userRepo.findByUsername(username);
    }

    public User getUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated()){
            return null;
        }
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = userRepo.findByUsername(userDetails.getUsername()).orElseThrow(
            () -> new UsernameNotFoundException("User not found"));
        return user;
    }

    public User addUser(User user){
        user.setPassword(passEncoder.encode(user.getPassword()));
        return this.userRepo.save(user);
    }

    public User updateUser(@RequestBody Map<String, String> body){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated()){
            return null;
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
            String encodedPassword = this.passEncoder.encode(body.get("password"));
            userToUpdate.setPassword(encodedPassword);
        }
        return this.userRepo.save(userToUpdate);
    }

    public User deleteUser(String username){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || !auth.isAuthenticated()){
            return null;
        }
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User userToDelete = userRepo.findByUsername(userDetails.getUsername()).orElseThrow(
            () -> new UsernameNotFoundException("User not found"));
            
        this.userRepo.delete(userToDelete);
        return userToDelete;
    }
    
}
