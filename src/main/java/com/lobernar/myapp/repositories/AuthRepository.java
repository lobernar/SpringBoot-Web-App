package com.lobernar.myapp.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

import com.lobernar.myapp.entities.User;

public interface AuthRepository extends CrudRepository<User, Long>{

    Optional<User> findById(long id);
    Optional<User> findByUsername(String username);
}
