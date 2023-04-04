package com.example.masterwork.user.services;

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
  public User getByUsername(String username) {
    return userRepository.findByUserName(username);
  }

  @Override
  public User getByEmail(String email) {
    return userRepository.findByEmail(email);
  }
}
