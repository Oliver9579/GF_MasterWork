package com.example.masterwork.registration.dtos;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class RegistrationDTO {

  @NotBlank
  private String firstName;
  @NotBlank
  private String lastName;
  @NotBlank
  private String username;
  @NotBlank
  @Email(message = "Please provide a valid email address.", regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
  private String email;
  @NotBlank
  @Size(min = 8, message = "Password must have 8 characters.")
  private String password;
  @NotBlank
  private String phoneNumber;
  @NotBlank
  private String address;

  public RegistrationDTO(String firstName, String lastName, String username,
                         String email, String password, String phoneNumber, String address) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.email = email;
    this.password = password;
    this.phoneNumber = phoneNumber;
    this.address = address;
  }

}