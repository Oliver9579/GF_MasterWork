package com.example.masterwork.menu.services;

import com.example.masterwork.menu.DTOs.MenuDTO;
import com.example.masterwork.menu.models.Menu;
import com.example.masterwork.user.models.User;

public interface MenuService {

  MenuDTO deleteMenuById(User user, Integer menuId);

  MenuDTO convertToMenuDTO(Menu menu);

}
