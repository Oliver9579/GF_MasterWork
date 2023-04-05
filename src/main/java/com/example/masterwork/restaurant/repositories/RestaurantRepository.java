package com.example.masterwork.restaurant.repositories;

import com.example.masterwork.restaurant.models.Restaurant;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends CrudRepository<Restaurant, Integer> {

  List<Restaurant> findAll();

  Optional<Restaurant> findById(Integer id);

}
