package com.example.masterwork.user.controllers;

import com.example.masterwork.user.models.User;
import com.example.masterwork.user.models.UserDTO;
import com.example.masterwork.user.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

@RestController
@AllArgsConstructor
public class UserController {

  private UserService userService;

  @GetMapping("/users")
  public ResponseEntity<UserDTO> getUser(UsernamePasswordAuthenticationToken auth) {
    String username = ((User) auth.getPrincipal()).getUsername();
    User user = userService.getByUsername(username);
    return ResponseEntity.ok().body(userService.convertUserToDTO(user));
  }

  @PutMapping("/users")
  public ResponseEntity<UserDTO> updateUserInformation(UsernamePasswordAuthenticationToken auth,
          @RequestBody User newUserInformation) throws SQLIntegrityConstraintViolationException {
    String username = ((User) auth.getPrincipal()).getUsername();
    User user = userService.getByUsername(username);
    return ResponseEntity.ok().body(userService.updateUser(user, newUserInformation));
  }

}
