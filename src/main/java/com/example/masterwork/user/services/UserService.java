package com.example.masterwork.user.services;

import com.example.masterwork.exceptions.UserNotFoundException;
import com.example.masterwork.user.models.User;

public interface UserService {

  User saveUser(User user);

  User getByUsername(String username) throws UserNotFoundException;

  User getByEmail(String email) throws UserNotFoundException;

}
