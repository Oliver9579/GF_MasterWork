package com.example.masterwork.exceptions;

public class StatusDoesNotBeTheSameException extends RuntimeException {

  public static final String MESSAGE = "The status does not be the same!";

  public StatusDoesNotBeTheSameException() {
    super(MESSAGE);
  }
}
