package com.example.masterwork.restaurant.controllers;

import com.example.masterwork.restaurant.DTOs.RestaurantDTO;
import com.example.masterwork.restaurant.DTOs.RestaurantListDTO;
import com.example.masterwork.restaurant.services.RestaurantService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class RestaurantController {

  private RestaurantService restaurantService;

  @GetMapping("restaurants")
  public ResponseEntity<RestaurantListDTO> getRestaurants() {
    return ResponseEntity.ok().body(restaurantService.getAllRestaurants());
  }

  @GetMapping("restaurants/{id}")
  public ResponseEntity<RestaurantDTO> getRestaurantsById(@PathVariable Integer id) {
    return ResponseEntity.ok().body(restaurantService.getById(id));
  }

}
