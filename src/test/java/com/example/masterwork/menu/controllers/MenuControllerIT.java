package com.example.masterwork.menu.controllers;

import com.example.masterwork.user.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class MenuControllerIT {

  @Autowired
  private MockMvc mockMvc;
  private ObjectMapper mapper;
  private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
          MediaType.APPLICATION_JSON.getSubtype(),
          StandardCharsets.UTF_8);
  private User user1;
  private Authentication user1Auth;

  @Before
  public void setUp() throws Exception {
    mapper = new ObjectMapper();
    user1 = new User(2, "", "", "Oli2",
            "", "", "", "");
    user1Auth = new UsernamePasswordAuthenticationToken(user1, null, null);

  }

  @Test
  public void deleteMenuById_should_returnTheDeletedMenu() throws Exception {
    mockMvc.perform(delete("/menus/6").principal(user1Auth))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(6)))
            .andExpect(jsonPath("$.name", is("Carbonara spagetti")))
            .andExpect(jsonPath("$.price", is(1800)));
  }

  @Test
  public void deleteMenuById_should_returnAnError_when_GivenMenuDoesNotExist() throws Exception {
    mockMvc.perform(delete("/menus/0").principal(user1Auth))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("Id not found")));
  }

}
