package com.example.masterwork.registration.controller;

import com.example.masterwork.registration.dtos.RegistrationDTO;
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
@AutoConfigureMockMvc(addFilters = false)
public class RegistrationControllerIT {

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
  public void registrationController_should_returnCorrectErrorMessage_when_EverythingAreMissing() throws Exception {
    mockMvc.perform(post("/register").contentType(contentType)
                    .content(mapper.writeValueAsString(new RegistrationDTO())))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", Matchers.allOf(
                    containsStringIgnoringCase("firstname"),
                    containsStringIgnoringCase("lastname"),
                    containsStringIgnoringCase("username"),
                    containsStringIgnoringCase("email"),
                    containsStringIgnoringCase("password"),
                    containsStringIgnoringCase("phonenumber"),
                    containsStringIgnoringCase("address"),
                    containsString("are required."))));
  }

  @Test
  public void registrationController_should_returnCorrectErrorMessage_when_requestBodyIsEmpty() throws Exception {
    mockMvc.perform(post("/register").contentType(contentType)
                    .content(""))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("Request body is empty.")));
  }


  @Test
  public void registrationController_should_returnCorrectErrorMessage_when_givenEmailNotValid() throws Exception {
    mockMvc.perform(post("/register").contentType(contentType)
                    .content(mapper.writeValueAsString(new RegistrationDTO("Szabó-Temple", "Olivér", "Oli",
                            "wrongEmail", "password", "+361234567", "budakeszi"))))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("Please provide a valid email address.")));
  }

  @Test
  public void registrationController_should_returnCorrectErrorMessage_when_givenPasswordNotValid() throws Exception {
    mockMvc.perform(post("/register").contentType(contentType)
                    .content(mapper.writeValueAsString(new RegistrationDTO("Szabó-Temple", "Olivér", "Oli",
                            "oli@gmail.com", "sortpas", "+361234567", "budakeszi"))))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status", is("error")))
            .andExpect(jsonPath("$.message", is("Password must have 8 characters.")));
  }

  @Test
  public void registrationController_should_returnCreatedUser_when_everyInputAreCorrect() throws Exception {
    mockMvc.perform(post("/register").contentType(contentType)
                    .content(mapper.writeValueAsString(new RegistrationDTO("Szabó-Temple", "Olivér", "Oli",
                            "oli@gmail.com", "password", "+361234567", "budakeszi"))))
            .andExpect(content().contentTypeCompatibleWith(contentType))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.fullName").value("Szabó-Temple Olivér"))
            .andExpect(jsonPath("$.username").value("Oli"))
            .andExpect(jsonPath("$.phoneNumber").value("+361234567"))
            .andExpect(jsonPath("$.address").value("budakeszi"));
  }

}
