package com.example.masterwork.exceptions;

public class UpdateUserNewInformationIsOccupiedException extends RuntimeException {
  public static final String MESSAGE = "Username or password is occupied.";

  public UpdateUserNewInformationIsOccupiedException() {
    super(MESSAGE);
  }
}
