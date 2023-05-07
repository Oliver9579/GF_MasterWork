package com.example.masterwork.menu.services;

import com.example.masterwork.exceptions.IdNotFoundException;
import com.example.masterwork.menu.DTOs.MenuDTO;
import com.example.masterwork.menu.models.Menu;
import com.example.masterwork.menu.repositories.MenuRepository;
import com.example.masterwork.user.models.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class MenuServiceImplTest {

  private MenuService menuService;
  @Mock
  private MenuRepository mockMenuRepository;

  private MenuDTO expectedMenu;
  private MenuDTO receivedMenu;

  @Before
  public void setup() {
    MockitoAnnotations.openMocks(this);
    this.menuService = Mockito.spy(new MenuServiceImpl(mockMenuRepository));
    expectedMenu = new MenuDTO();
    receivedMenu = new MenuDTO();

  }

  @Test
  public void testDeleteMenuById_Success() {
    User user = new User();
    Integer menuId = 1;
    Menu menu = new Menu(menuId, "Test Menu", "Test Description", 10, new ArrayList<>(), new ArrayList<>());
    when(mockMenuRepository.findById(menuId)).thenReturn(Optional.of(menu));

    MenuDTO result = menuService.deleteMenuById(user, menuId);

    assertNotNull(result);
    assertEquals(menu.getId(), result.getId());
    assertEquals(menu.getName(), result.getName());
    assertEquals(menu.getDescription(), result.getDescription());
    assertEquals(menu.getPrice(), result.getPrice());
    verify(mockMenuRepository, times(1)).delete(menu);
  }

  @Test(expected = IdNotFoundException.class)
  public void testDeleteMenuById_IdNotFound() {
    User user = new User();
    Integer menuId = 1;
    when(mockMenuRepository.findById(menuId)).thenReturn(Optional.empty());

    menuService.deleteMenuById(user, menuId);

    verify(mockMenuRepository, times(0)).delete(any());

  }

  @Test
  public void convertToMenuDTO_should_return_correctMenuDTO_when_menuGiven() {
    Menu menu = new Menu(1, "Sajtburger", "finom sajburger",
            1000, new ArrayList<>(), new ArrayList<>());
    expectedMenu = new MenuDTO(1, "Sajtburger", "finom sajburger", 1000);

    receivedMenu = menuService.convertToMenuDTO(menu);

    assertEquals(expectedMenu, receivedMenu);
  }

}