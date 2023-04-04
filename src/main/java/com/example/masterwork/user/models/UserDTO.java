package com.example.masterwork.user.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

  private Integer id;
  private String fullName;
  private String username;
  private String email;
  private String phoneNumber;
  private String address;

}
