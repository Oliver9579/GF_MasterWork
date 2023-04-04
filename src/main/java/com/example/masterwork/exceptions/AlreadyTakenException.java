package com.example.masterwork.exceptions;

public class AlreadyTakenException extends RuntimeException {

  public AlreadyTakenException(String message) {
    super(message);
  }

}