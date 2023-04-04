package com.example.masterwork.registration.controller;

import com.example.masterwork.errorhandling.ErrorMessage;
import com.example.masterwork.exceptions.AlreadyTakenException;
import com.example.masterwork.exceptions.InvalidLengthException;
import com.example.masterwork.exceptions.MissingInputException;
import com.example.masterwork.registration.dtos.RegistrationDTO;
import com.example.masterwork.registration.dtos.RegistrationResponseDTO;
import com.example.masterwork.registration.service.RegistrationService;
import com.example.masterwork.user.models.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class RegistrationController {

  private RegistrationService registrationService;

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody @Valid RegistrationDTO rdto) {
    try {
      User user = registrationService.register(rdto);
      return ResponseEntity.status(201).body(new RegistrationResponseDTO(
              user.getId(), user.getFirstName() + " " + user.getLastName(), user.getUserName(),
              user.getPhoneNumber(), user.getAddress()));
    } catch (MissingInputException e) {
      return ResponseEntity.status(400).body(new ErrorMessage(e.getMessage()));
    } catch (AlreadyTakenException e) {
      return ResponseEntity.status(409).body(new ErrorMessage(e.getMessage()));
    } catch (InvalidLengthException e) {
      return ResponseEntity.status(406).body(new ErrorMessage(e.getMessage()));
    }
  }

}