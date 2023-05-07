package com.example.masterwork.registration.controller;

import com.example.masterwork.errorhandling.ErrorMessage;
import com.example.masterwork.exceptions.AlreadyTakenException;
import com.example.masterwork.exceptions.InvalidLengthException;
import com.example.masterwork.exceptions.MissingInputException;
import com.example.masterwork.registration.dtos.RegistrationDTO;
import com.example.masterwork.registration.dtos.RegistrationResponseDTO;
import com.example.masterwork.registration.service.RegistrationService;
import com.example.masterwork.user.models.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@Tag(name = "users", description = "User session related operations")
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RegistrationController {

  private RegistrationService registrationService;

  @ApiResponses(value = {
          @ApiResponse(responseCode = "201",
                  description = "if all required parameters provided and username and email are unique, returns a HTTP 201 status with the User object",
                  content = @Content(
                  schema = @Schema(implementation = RegistrationResponseDTO.class))),
          @ApiResponse(responseCode = "400", description = "if a required parameter is missing, returns a HTTP 400 status and error message", content = @Content(
                  schema = @Schema(implementation = ErrorMessage.class))),
          @ApiResponse(responseCode = "409", description = "if all parameter provided but username or email are occupied", content = @Content(
                  schema = @Schema(implementation = ErrorMessage.class)))})
  @PostMapping("/register")
  @Operation(summary = "Create user")
  public ResponseEntity<?> register(@Parameter()@RequestBody @Valid RegistrationDTO rdto) {
    try {
      User user = registrationService.register(rdto);
      return ResponseEntity.status(201).body(new RegistrationResponseDTO(
              user.getId(), user.getFirstName() + " " + user.getLastName(), user.getUsername(),
              user.getPhoneNumber(), user.getAddress()));
    } catch (MissingInputException e) {
      return ResponseEntity.status(400).body(new ErrorMessage(e.getMessage()));
    } catch (AlreadyTakenException e) {
      return ResponseEntity.status(409).body(new ErrorMessage(e.getMessage()));
    }
  }

}