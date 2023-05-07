package com.example.masterwork.restaurant.services;

import com.example.masterwork.exceptions.IdNotFoundException;
import com.example.masterwork.restaurant.DTOs.RestaurantDTO;
import com.example.masterwork.restaurant.DTOs.RestaurantListDTO;
import com.example.masterwork.restaurant.models.Restaurant;
import com.example.masterwork.restaurant.repositories.RestaurantRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RestaurantServiceImplTest {

  @Mock
  private RestaurantRepository mockRestaurantRepository;

  private RestaurantServiceImpl restaurantService;

  private RestaurantDTO restaurantDTO1;
  private RestaurantDTO restaurantDTO2;
  private RestaurantListDTO restaurantListDTO;

  @Before
  public void setup() {
    MockitoAnnotations.openMocks(this);
    restaurantService = new RestaurantServiceImpl(mockRestaurantRepository);
    restaurantDTO1 = new RestaurantDTO(1, "Mcdonalds", "+363012345",
            "budakeszi", new ArrayList<>());
    restaurantDTO2 = new RestaurantDTO(2, "Burger King", "+363012345",
            "budakeszi", new ArrayList<>());
    restaurantListDTO = new RestaurantListDTO(Arrays.asList(restaurantDTO1, restaurantDTO2));
  }

  @Test
  public void getAllRestaurants_should_return_allRestaurants() {
    List<Restaurant> restaurants = Arrays.asList(
            new Restaurant(1, "Mcdonalds", "+363012345",
                    "budakeszi", new ArrayList<>(), new ArrayList<>()),
            new Restaurant(2, "Burger King", "+363012345",
                    "budakeszi", new ArrayList<>(), new ArrayList<>())
    );
    when(mockRestaurantRepository.findAll()).thenReturn(restaurants);

    RestaurantListDTO result = restaurantService.getAllRestaurants();

    assertEquals(restaurantListDTO, result);
  }

  @Test
  public void getById_should_return_correctRestaurant() {
    int id = 1;
    Restaurant restaurant = new Restaurant(id, "Mcdonalds", "+363012345",
            "budakeszi", new ArrayList<>(), new ArrayList<>());
    when(mockRestaurantRepository.findById(id)).thenReturn(Optional.of(restaurant));

    Restaurant result = restaurantService.getById(id);

    assertEquals(restaurant, result);
  }

  @Test(expected = IdNotFoundException.class)
  public void getById_should_throw_IdNotFoundException() {
    int id = 1;
    when(mockRestaurantRepository.findById(id)).thenReturn(Optional.empty());

    restaurantService.getById(id);
  }

  @Test
  public void convertToDTOList_should_return_correctDTOList() {
    List<Restaurant> restaurants = Arrays.asList(
            new Restaurant(1, "Mcdonalds", "+363012345",
                    "budakeszi", new ArrayList<>(), new ArrayList<>()),
            new Restaurant(2, "Burger King", "+363012345",
                    "budakeszi", new ArrayList<>(), new ArrayList<>())
    );

    RestaurantListDTO result = restaurantService.convertToDTOList(restaurants);

    assertEquals(restaurantListDTO, result);
  }

  @Test
  public void convertToRestaurantDTO_should_return_correctDTO() {
    Restaurant restaurant = new Restaurant(1, "Mcdonalds", "+363012345",
            "budakeszi", new ArrayList<>(), new ArrayList<>());

    RestaurantDTO result = restaurantService.convertToRestaurantDTO(restaurant);

    assertEquals(restaurantDTO1, result);
  }
}