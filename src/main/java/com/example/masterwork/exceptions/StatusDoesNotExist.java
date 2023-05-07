package com.example.masterwork.exceptions;

public class StatusDoesNotExist extends RuntimeException {

  public static final String MESSAGE = "This status is does not exist!";

  public StatusDoesNotExist() {
    super(MESSAGE);
  }
}
