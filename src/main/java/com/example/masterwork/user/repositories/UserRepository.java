package com.example.masterwork.user.repositories;

import com.example.masterwork.user.models.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

  Optional<User> findByEmail(String email);

  Optional<User> findByUserName(String username);
}
