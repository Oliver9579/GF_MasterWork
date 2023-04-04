package com.example.masterwork.exceptions;

public class WrongPasswordException extends RuntimeException {

  public static final String MESSAGE = "Username or password is incorrect.";

  public WrongPasswordException() {
    super(MESSAGE);
  }

}
