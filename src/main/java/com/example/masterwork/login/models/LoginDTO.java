package com.example.masterwork.login.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

  @NotBlank
  private String username;
  @NotBlank
  private String password;

}
