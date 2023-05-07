package com.example.masterwork.errorhandling;

import com.example.masterwork.exceptions.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@AllArgsConstructor
public class RestControllerExceptionHandler {

  private ExceptionHandlerUtility exceptionHandlerUtility;

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorMessage> handleMissingRequestFields(MethodArgumentNotValidException e) {
    List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
    List<FieldError> missingErrors = fieldErrors.stream()
            .filter(error -> error.getCode().equals("NotNull") || error.getCode().equals("NotBlank"))
            .collect(Collectors.toList());
    String message = fieldErrors.get(0).getDefaultMessage();
    if (!missingErrors.isEmpty()) {
      message = exceptionHandlerUtility.createErrorMessageForMissingFields(missingErrors);
    }
    return ResponseEntity.badRequest().body(new ErrorMessage(message));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ErrorMessage> handleMissingRequestBody() {
    return ResponseEntity.badRequest().body(new ErrorMessage("Request body is empty."));
  }

  @ExceptionHandler(WrongPasswordException.class)
  public ResponseEntity<ErrorMessage> handleLoginOrRegistrationIncorrect() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorMessage(WrongPasswordException.MESSAGE));
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ErrorMessage> handleIncorrectLoginDetails() {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorMessage(UserNotFoundException.MESSAGE));
  }

  @ExceptionHandler(IdNotFoundException.class)
  public ResponseEntity<ErrorMessage> handleIdNotFound() {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(IdNotFoundException.MESSAGE));
  }

  @ExceptionHandler(ForbiddenActionException.class)
  public ResponseEntity<ErrorMessage> handleForbiddenAction() {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorMessage(ForbiddenActionException.MESSAGE));
  }

  @ExceptionHandler(UpdateUserNewInformationIsOccupiedException.class)
  public ResponseEntity<ErrorMessage> handleNewInformationIsOccupiedException() {
    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ErrorMessage(UpdateUserNewInformationIsOccupiedException.MESSAGE));
  }

  @ExceptionHandler(MissingOrderItemsException.class)
  public ResponseEntity<ErrorMessage> handleOrderItemIsEmptyInNewOrder() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(MissingOrderItemsException.MESSAGE));
  }

  @ExceptionHandler(StatusDoesNotExist.class)
  public ResponseEntity<ErrorMessage> handleStatusDoesNotExist() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(StatusDoesNotExist.MESSAGE));
  }

  @ExceptionHandler(StatusDoesNotBeTheSameException.class)
  public ResponseEntity<ErrorMessage> handleStatusDoesNotChange() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(StatusDoesNotBeTheSameException.MESSAGE));
  }

}