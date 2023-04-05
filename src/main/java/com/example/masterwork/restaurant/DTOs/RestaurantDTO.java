package com.example.masterwork.restaurant.DTOs;

import com.example.masterwork.menu.models.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class RestaurantDTO {

  private Integer id;
  private String name;
  private String phoneNumber;
  private String address;
  private List<Menu> menus;

}
