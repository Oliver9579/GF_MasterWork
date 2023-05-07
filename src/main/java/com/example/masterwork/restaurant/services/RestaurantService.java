package com.example.masterwork.restaurant.services;

import com.example.masterwork.restaurant.DTOs.RestaurantDTO;
import com.example.masterwork.restaurant.DTOs.RestaurantListDTO;
import com.example.masterwork.restaurant.models.Restaurant;

import java.util.List;

public interface RestaurantService {

  RestaurantListDTO getAllRestaurants();

  RestaurantListDTO convertToDTOList(List<Restaurant> restaurants);

  Restaurant getById(Integer id);

  RestaurantDTO convertToRestaurantDTO(Restaurant restaurant);

}
