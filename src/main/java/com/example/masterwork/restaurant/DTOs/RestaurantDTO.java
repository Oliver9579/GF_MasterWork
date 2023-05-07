package com.example.masterwork.restaurant.DTOs;

import com.example.masterwork.menu.models.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {

  @NotNull
  private Integer id;
  @NotBlank
  private String name;
  @NotBlank
  private String phoneNumber;
  @NotBlank
  private String address;
  @NotNull
  private List<Menu> menus;

}
