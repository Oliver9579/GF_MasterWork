package com.example.masterwork.registration.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class RegistrationDTO {

  @NotBlank
  @JsonAlias("firstname")
  private String firstName;
  @NotBlank
  @JsonAlias("lastname")
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
  @JsonAlias("phonenumber")
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