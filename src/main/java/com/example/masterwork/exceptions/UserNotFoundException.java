package com.example.masterwork.exceptions;

public class UserNotFoundException extends RuntimeException {

  public static final String MESSAGE = "No such user.";

  public UserNotFoundException() {
    super(MESSAGE);
  }
}
