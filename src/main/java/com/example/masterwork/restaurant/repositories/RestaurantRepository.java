package com.example.masterwork.restaurant.repositories;

import com.example.masterwork.restaurant.models.Restaurant;
import org.springframework.data.repository.CrudRepository;

public interface RestaurantRepository extends CrudRepository<Restaurant, Integer> {
}
