package com.example.masterwork.user.controllers;

import com.example.masterwork.restaurant.DTOs.RestaurantDTO;
import com.example.masterwork.user.models.User;
import com.example.masterwork.user.models.UserDTO;
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

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerIT {

  @Autowired
  private MockMvc mockMvc;
  private ObjectMapper mapper;
  private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
          MediaType.APPLICATION_JSON.getSubtype(),
          StandardCharsets.UTF_8);
  private User user1;
  private User user2;
  private User emptyOrderListUser;
  private User userNotExist;
  private Authentication user1Auth;
  private Authentication user2Auth;
  private Authentication emptyOrderListUserAuth;
  private Authentication authForNotExistUser;

  @Before
  public void setUp() throws Exception {
    mapper = new ObjectMapper();
    user1 = new User(2, "", "", "Oli2",
            "", "", "", "");
    user2 = new User(3, "", "", "Oli3",
            "", "", "", "");
    emptyOrderListUser = new User(4, "", "", "emptyOrder",
            "", "", "", "");
    userNotExist = new User(100, "", "", "Oli100",
            "", "", "", "");
    user1Auth = new UsernamePasswordAuthenticationToken(user1, null, null);
    user2Auth = new UsernamePasswordAuthenticationToken(user2, null, null);
    emptyOrderListUserAuth = new UsernamePasswordAuthenticationToken(emptyOrderListUser, null, null);
    authForNotExistUser = new UsernamePasswordAuthenticationToken(userNotExist, null, null);

  }

  @Test
  public void getAllRestaurant_should_returnARestaurantList() throws Exception {
    UserDTO expected = new UserDTO(2, "Szabo-Temple Oliver", "Oli2",
            "Oli2@gmail.com", "+3630123456", "budakeszi");
    MvcResult result = mockMvc.perform(get("/users").principal(user1Auth))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isOk())
            .andReturn();

    UserDTO receivedUser = mapper.readValue(result.getResponse().getContentAsString(), UserDTO.class);

    assertEquals(expected, receivedUser);
  }

  @Test
  public void getAllRestaurant_should_returnAnError_when_userDoesNotExist() throws Exception {
    mockMvc.perform(get("/users").principal(authForNotExistUser))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.status", Matchers.is("error")))
            .andExpect(jsonPath("$.message", Matchers.is("No such user.")));

  }

}