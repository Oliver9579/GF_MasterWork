package com.example.masterwork.user.services;

import com.example.masterwork.exceptions.ForbiddenActionException;
import com.example.masterwork.exceptions.UserNotFoundException;
import com.example.masterwork.order.models.Order;
import com.example.masterwork.security.password.PasswordService;
import com.example.masterwork.user.models.User;
import com.example.masterwork.user.models.UserDTO;
import com.example.masterwork.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private PasswordService passwordService;

  @Override
  public User saveUser(User user) {
    return userRepository.save(user);
  }

  @Override
  public User getByUsername(String username) throws UserNotFoundException {
    return userRepository.findByUserName(username).orElseThrow(UserNotFoundException::new);
  }

  @Override
  public User getById(Integer userId) {
    return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
  }

  @Override
  public User getByEmail(String email) throws UserNotFoundException {
    return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
  }

  @Override
  public UserDTO convertUserToDTO(User user) {
    return new UserDTO(user.getId(), user.getFirstName() + " " + user.getLastName(),
            user.getUsername(), user.getEmail(), user.getPhoneNumber(), user.getAddress());
  }

  @Override
  public Boolean isUserIdMatching(Integer userId, Order order) {
    if (userId == null || !userId.equals(order.getUser().getId()))
      throw new ForbiddenActionException();
    return true;
  }

}
