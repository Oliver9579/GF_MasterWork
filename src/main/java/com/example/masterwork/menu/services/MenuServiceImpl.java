package com.example.masterwork.menu.services;

import com.example.masterwork.exceptions.IdNotFoundException;
import com.example.masterwork.menu.DTOs.MenuDTO;
import com.example.masterwork.menu.models.Menu;
import com.example.masterwork.menu.repositories.MenuRepository;
import com.example.masterwork.user.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MenuServiceImpl implements MenuService {

  private MenuRepository menuRepository;

  @Override
  public MenuDTO deleteMenuById(User user, Integer menuId) {
    Menu menu = menuRepository.findById(menuId).orElseThrow(IdNotFoundException::new);

    menuRepository.delete(menu);
    return convertToMenuDTO(menu);
  }

  @Override
  public MenuDTO convertToMenuDTO(Menu menu) {
    return new MenuDTO(menu.getId(), menu.getName(), menu.getDescription(), menu.getPrice());
  }

}
