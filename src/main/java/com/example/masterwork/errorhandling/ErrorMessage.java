package com.example.masterwork.errorhandling;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {

  private String status;
  private String message;

  public ErrorMessage(String message) {
    this.status = "error";
    this.message = message;
  }

}
