package com.example.masterwork.exceptions;

public class MissingOrderItemsException extends RuntimeException {

  public static final String MESSAGE = "Order items list cannot be empty!";

  public MissingOrderItemsException() {
    super(MESSAGE);
  }

}
