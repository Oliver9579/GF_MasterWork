package com.example.masterwork.registration.service;

import com.example.masterwork.exceptions.AlreadyTakenException;
import com.example.masterwork.exceptions.UserNotFoundException;
import com.example.masterwork.registration.dtos.RegistrationDTO;
import com.example.masterwork.security.password.PasswordService;
import com.example.masterwork.user.models.User;
import com.example.masterwork.user.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

  private UserService userService;
  private PasswordService passwordService;

  public void validate(RegistrationDTO rdto) throws AlreadyTakenException {
    try {
      userService.getByUsername(rdto.getUsername());
      throw new AlreadyTakenException("Username is already taken.");
    } catch (UserNotFoundException e) {
    }
    try {
      userService.getByEmail(rdto.getEmail());
      throw new AlreadyTakenException("Email address is already taken.");
    } catch (UserNotFoundException e) {
    }
  }

  public User register(RegistrationDTO rdto) throws AlreadyTakenException {
    validate(rdto);
    User user = convertToUser(rdto);
    return userService.saveUser(user);
  }

  private User convertToUser(RegistrationDTO rdto) {
    return new User(rdto.getFirstName(), rdto.getLastName(), rdto.getUsername(), rdto.getEmail(),
            passwordService.passwordEncoding(rdto.getPassword()), rdto.getPhoneNumber(), rdto.getAddress());
  }

}
