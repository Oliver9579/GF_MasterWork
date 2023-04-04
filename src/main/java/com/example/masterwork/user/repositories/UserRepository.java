package com.example.masterwork.user.repositories;

import com.example.masterwork.user.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

  User findByEmail(String email);

  User findByUserName(String username);
}
