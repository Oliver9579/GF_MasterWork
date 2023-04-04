package com.example.masterwork.errorhandling;

import lombok.AllArgsConstructor;
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
}