package com.example.masterwork.menu.controllers;

import com.example.masterwork.menu.DTOs.MenuDTO;
import com.example.masterwork.menu.services.MenuService;
import com.example.masterwork.user.models.User;
import com.example.masterwork.user.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class MenuController {

  private UserService userService;
  private MenuService menuService;

  @DeleteMapping("/menus/{id}")
  public ResponseEntity<MenuDTO> deleteMenuById(UsernamePasswordAuthenticationToken auth,
                                                @PathVariable(name = "id") Integer menuId) {
    Integer userId = ((User) auth.getPrincipal()).getId();
    User user = userService.getById(userId);
    return ResponseEntity.ok().body(menuService.deleteMenuById(user, menuId));
  }

}
