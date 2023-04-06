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

import java.sql.SQLIntegrityConstraintViolationException;

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

  @Override
  public UserDTO updateUser(User user, User newUserInformation) throws SQLIntegrityConstraintViolationException {
    int userId = user.getId();
    user = newUserInformation;
    user.setId(userId);
    user.setPassword(passwordService.passwordEncoding(user.getPassword()));
    userRepository.save(user);
    return convertUserToDTO(user);
  }

}
