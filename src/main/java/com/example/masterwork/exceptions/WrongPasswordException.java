package com.example.masterwork.exceptions;

public class WrongPasswordException extends RuntimeException {

  public static final String MESSAGE = "Password is incorrect.";

  public WrongPasswordException() {
    super(MESSAGE);
  }

}
