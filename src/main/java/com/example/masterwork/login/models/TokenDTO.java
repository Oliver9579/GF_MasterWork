package com.example.masterwork.login.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {

  private String status;
  private String token;

  public TokenDTO(String token) {
    this.status = "ok";
    this.token = token;
  }

}