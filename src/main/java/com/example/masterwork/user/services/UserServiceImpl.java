package com.example.masterwork.user.services;

import com.example.masterwork.exceptions.UserNotFoundException;
import com.example.masterwork.user.models.User;
import com.example.masterwork.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;

  @Override
  public User saveUser(User user) {
    return userRepository.save(user);
  }

  @Override
  public User getByUsername(String username) throws UserNotFoundException {
    return userRepository.findByUserName(username).orElseThrow(UserNotFoundException::new);
  }

  @Override
  public User getByEmail(String email) throws UserNotFoundException {
    return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
  }
}
