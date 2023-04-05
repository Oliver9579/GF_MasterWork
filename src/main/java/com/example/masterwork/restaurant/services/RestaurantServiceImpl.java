package com.example.masterwork.restaurant.services;

import com.example.masterwork.exceptions.IdNotFoundException;
import com.example.masterwork.restaurant.DTOs.RestaurantDTO;
import com.example.masterwork.restaurant.DTOs.RestaurantListDTO;
import com.example.masterwork.restaurant.models.Restaurant;
import com.example.masterwork.restaurant.repositories.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

  private RestaurantRepository restaurantRepository;

  @Override
  public RestaurantListDTO getAllRestaurants() {
    return convertToDTOList(restaurantRepository.findAll());
  }

  @Override
  public RestaurantListDTO convertToDTOList(List<Restaurant> restaurants) {
    return new RestaurantListDTO(restaurants.stream()
            .map(this::convertToRestaurantDTO)
            .collect(Collectors.toList()));
  }

  @Override
  public RestaurantDTO getById(Integer id) {
    return convertToRestaurantDTO(restaurantRepository.findById(id).orElseThrow(IdNotFoundException::new));
  }


  private RestaurantDTO convertToRestaurantDTO(Restaurant restaurant) {
    return new RestaurantDTO(restaurant.getId(), restaurant.getName(), restaurant.getPhoneNumber(),
            restaurant.getAddress(), restaurant.getMenus());
  }
}
