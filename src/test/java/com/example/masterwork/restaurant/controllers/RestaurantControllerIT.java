package com.example.masterwork.restaurant.controllers;

import com.example.masterwork.order.DTOs.OrderDTO;
import com.example.masterwork.restaurant.DTOs.RestaurantDTO;
import com.example.masterwork.user.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static com.example.masterwork.order.utils.OrderStatus.ORDER_CONFIRMED;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class RestaurantControllerIT {

  @Autowired
  private MockMvc mockMvc;
  private ObjectMapper mapper;
  private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
          MediaType.APPLICATION_JSON.getSubtype(),
          StandardCharsets.UTF_8);
  private User user1;
  private User user2;
  private User emptyOrderListUser;
  private Authentication user1Auth;
  private Authentication user2Auth;
  private Authentication emptyOrderListUserAuth;

  @Before
  public void setUp() throws Exception {
    mapper = new ObjectMapper();
    user1 = new User(2, "", "", "Oli2",
            "", "", "", "");
    user2 = new User(3, "", "", "Oli3",
            "", "", "", "");
    emptyOrderListUser = new User(4, "", "", "emptyOrder",
            "", "", "", "");
    user1Auth = new UsernamePasswordAuthenticationToken(user1, null, null);
    user2Auth = new UsernamePasswordAuthenticationToken(user2, null, null);
    emptyOrderListUserAuth = new UsernamePasswordAuthenticationToken(emptyOrderListUser, null, null);

  }

  @Test
  public void getAllRestaurant_should_returnARestaurantList() throws Exception {
    mockMvc.perform(get("/restaurants").principal(user1Auth))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.restaurants").isArray());
  }

  @Test
  public void getRestaurantById_should_returnARestaurant_when_restaurantIdGiven() throws Exception {
    RestaurantDTO expectedRestaurant = new RestaurantDTO(1, "McDonalds", "+3612345",
            "McDonalds utca", new ArrayList<>());
    MvcResult result = mockMvc.perform(get("/restaurants/1").principal(user1Auth))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isOk())
            .andReturn();

    RestaurantDTO receiverRestaurant = mapper.readValue(result.getResponse().getContentAsString(), RestaurantDTO.class);

    assertEquals(expectedRestaurant.getId(), receiverRestaurant.getId());
    assertEquals(expectedRestaurant.getName(), receiverRestaurant.getName());
    assertEquals(expectedRestaurant.getPhoneNumber(), receiverRestaurant.getPhoneNumber());
    assertEquals(expectedRestaurant.getAddress(), receiverRestaurant.getAddress());
  }

  @Test
  public void getRestaurantById_should_returnAnError_when_restaurantIdDoesNotExist() throws Exception {
    mockMvc.perform(get("/restaurants/0").principal(user1Auth))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().is(404))
            .andExpect(jsonPath("$.status", Matchers.is("error")))
            .andExpect(jsonPath("$.message", Matchers.is("Id not found")));
  }


}