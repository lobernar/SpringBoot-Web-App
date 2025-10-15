package com.lobernar.myapp.service;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.lobernar.myapp.entities.User;
import com.lobernar.myapp.repositories.UserRepository;


/**
 * Service class for loading user-specific data for Spring Security authentication.
 * Implements UserDetailsService to provide user details from the database.
 */

@Component
public class MyUserDetailService implements UserDetailsService{

    private final UserRepository userRepo;

    /**
     * Constructor for MyUserDetailService.
     * @param ur UserRepository instance for accessing user data.
     */
    @Autowired
    public MyUserDetailService(UserRepository ur){
        this.userRepo = ur;
    }

    /**
     * Load user details by user ID.
     * @param userId the ID of the user to load.
     * @return UserDetails object containing user information.
     * @throws UsernameNotFoundException if the user is not found.
     */
    public UserDetails loadUserById(Integer userId) throws UsernameNotFoundException {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
    }

    /**
     * Load user details by username.
     * @param username the username of the user to load.
     * @return UserDetails object containing user information.
     * @throws UsernameNotFoundException if the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optUser = this.userRepo.findByUsername(username);

        if(!optUser.isPresent() || optUser.get() == null){
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        User user = optUser.get();
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(user.getRole())));
    }
}
