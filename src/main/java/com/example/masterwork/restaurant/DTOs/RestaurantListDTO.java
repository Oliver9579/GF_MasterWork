package com.example.masterwork.restaurant.DTOs;

import com.example.masterwork.restaurant.models.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class RestaurantListDTO {

  List<RestaurantDTO> restaurants;

  public RestaurantListDTO(List<RestaurantDTO> restaurants) {
    this.restaurants = restaurants;
  }
}
