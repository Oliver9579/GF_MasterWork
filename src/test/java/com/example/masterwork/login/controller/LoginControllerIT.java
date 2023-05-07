package com.example.masterwork.login.controller;


import com.example.masterwork.login.models.LoginDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerIT {

  @Autowired
  private MockMvc mockMvc;
  private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
          MediaType.APPLICATION_JSON.getSubtype(),
          StandardCharsets.UTF_8);
  private ObjectMapper mapper;

  @Before
  public void setup() {
    mapper = new ObjectMapper();
  }

  @Test
  public void loginController_should_returnCorrectErrorMessage_when_passwordFieldIsMissing() throws Exception {
    mockMvc.perform(
                    post("/login")
                            .contentType(contentType)
                            .content(mapper.writeValueAsString(new LoginDTO("username", null))))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("Password is required.")));
  }

  @Test
  public void loginController_should_returnCorrectErrorMessage_when_passwordFieldIsEmpty() throws Exception {
    mockMvc.perform(
                    post("/login")
                            .contentType(contentType)
                            .content(mapper.writeValueAsString(new LoginDTO("username", ""))))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("Password is required.")));
  }

  @Test
  public void loginController_should_returnCorrectErrorMessage_when_usernameFieldIsMissing() throws Exception {
    mockMvc.perform(
                    post("/login")
                            .contentType(contentType)
                            .content(mapper.writeValueAsString(new LoginDTO(null, "password"))))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("Username is required.")));
  }

  @Test
  public void loginController_should_returnCorrectErrorMessage_when_usernameFieldIsEmpty() throws Exception {
    mockMvc.perform(
                    post("/login")
                            .contentType(contentType)
                            .content(mapper.writeValueAsString(new LoginDTO("", "password"))))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("Username is required.")));
  }

  @Test
  public void loginController_should_returnCorrectErrorMessage_when_requestBodyIsEmpty() throws Exception {
    mockMvc.perform(
                    post("/login")
                            .contentType(contentType)
                            .content("{}"))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", Matchers.allOf(
                    containsStringIgnoringCase("username"),
                    containsStringIgnoringCase("password"),
                    containsString("are required.")
            )));
  }

  @Test
  public void loginController_should_returnCorrectErrorMessage_when_usernameDoesNotExist() throws Exception {
    mockMvc.perform(
                    post("/login")
                            .contentType(contentType)
                            .content(mapper.writeValueAsString(new LoginDTO("invalidUsername", "password"))))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().is(401))
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("No such user.")));
  }

  @Test
  public void loginController_should_returnCorrectErrorMessage_when_passwordIsIncorrect() throws Exception {
    mockMvc.perform(
                    post("/login")
                            .contentType(contentType)
                            .content(mapper.writeValueAsString(new LoginDTO("Oli2", "shortpw"))))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().is(401))
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("Password is incorrect.")));
  }

  @Test
  public void loginController_should_returnAToken_when_correctUsernameAndPasswordAreGiven() throws Exception {
    mockMvc.perform(
                    post("/login")
                            .contentType(contentType)
                            .content(mapper.writeValueAsString(new LoginDTO("Oli2", "password"))))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.status", is("ok")))
            .andExpect(jsonPath("$.token", is(Matchers.not(Matchers.emptyString()))));
  }

}
