package com.example.masterwork.registration.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class RegistrationResponseDTO {

  private Integer id;
  private String fullName;
  private String username;
  private String phoneNumber;
  private String address;

}
