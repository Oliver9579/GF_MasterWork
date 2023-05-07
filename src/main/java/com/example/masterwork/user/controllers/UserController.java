package com.example.masterwork.user.controllers;

import com.example.masterwork.user.models.User;
import com.example.masterwork.user.models.UserDTO;
import com.example.masterwork.user.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class UserController {

  private UserService userService;

  @GetMapping("/users")
  public ResponseEntity<UserDTO> getUser(UsernamePasswordAuthenticationToken auth) {
    Integer userId = ((User) auth.getPrincipal()).getId();
    User user = userService.getById(userId);
    return ResponseEntity.ok().body(userService.convertUserToDTO(user));
  }

}
