package com.example.masterwork.registration.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationResponseDTO {

  private Integer id;
  private String fullName;
  private String username;
  private String phoneNumber;
  private String address;

}
