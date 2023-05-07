package com.example.masterwork.errorhandling;

import com.example.masterwork.exceptions.ForbiddenActionException;
import com.example.masterwork.exceptions.IdNotFoundException;
import com.example.masterwork.exceptions.UserNotFoundException;
import com.example.masterwork.exceptions.WrongPasswordException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class RestControllerExceptionHandlerTest {

  private RestControllerExceptionHandler restControllerExceptionHandler;
  private List<FieldError> fieldErrors;
  private String errorMessage;
  private String receivedMessage;
  private HttpStatus expectedStatus;
  private HttpStatus receivedStatus;
  private ResponseEntity<ErrorMessage> response;
  @Mock
  private BindingResult bindingResult;
  @Mock
  private FieldError fieldError;
  @Mock
  private FieldError fieldErrorTwo;
  @Mock
  private FieldError fieldErrorThree;
  @Mock
  private ExceptionHandlerUtility exceptionHandlerUtility;
  @Mock
  private MethodArgumentNotValidException methodArgumentNotValidException;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
    restControllerExceptionHandler = new RestControllerExceptionHandler(exceptionHandlerUtility);
    fieldErrors = new ArrayList<>();
    fieldErrors.add(fieldError);
  }

  @Test
  public void handleMissingRequestFields_should_returnCorrectMessage_when_singleFieldIsMissing() {
    when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
    when(methodArgumentNotValidException.getBindingResult().getFieldErrors()).thenReturn(fieldErrors);
    when(fieldError.getCode()).thenReturn("NotBlank");
    when(exceptionHandlerUtility.createErrorMessageForMissingFields(fieldErrors))
      .thenReturn("Username is required.");

    response = restControllerExceptionHandler.handleMissingRequestFields(methodArgumentNotValidException);
    errorMessage = "Username is required.";
    receivedMessage = response.getBody().getMessage();
    expectedStatus = HttpStatus.BAD_REQUEST;
    receivedStatus = response.getStatusCode();

    assertEquals(errorMessage, receivedMessage);
    assertEquals(expectedStatus, receivedStatus);
  }

  @Test
  public void handleMissingRequestFields_should_returnCorrectMessage_when_twoFieldsAreMissing() {
    fieldErrors.add(fieldErrorTwo);
    when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
    when(methodArgumentNotValidException.getBindingResult().getFieldErrors()).thenReturn(fieldErrors);
    when(fieldError.getCode()).thenReturn("NotBlank");
    when(fieldErrorTwo.getCode()).thenReturn("NotBlank");
    when(exceptionHandlerUtility.createErrorMessageForMissingFields(fieldErrors))
      .thenReturn("Username and password are required.");

    response = restControllerExceptionHandler.handleMissingRequestFields(methodArgumentNotValidException);
    errorMessage = "Username and password are required.";
    receivedMessage = response.getBody().getMessage();
    expectedStatus = HttpStatus.BAD_REQUEST;
    receivedStatus = response.getStatusCode();

    assertEquals(errorMessage, receivedMessage);
    assertEquals(expectedStatus, receivedStatus);
  }

  @Test
  public void handleMissingRequestFields_should_returnCorrectMessage_when_multipleFieldsAreMissing() {
    fieldErrors.add(fieldErrorTwo);
    fieldErrors.add(fieldErrorThree);
    when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
    when(methodArgumentNotValidException.getBindingResult().getFieldErrors()).thenReturn(fieldErrors);
    when(fieldError.getCode()).thenReturn("NotBlank");
    when(fieldErrorTwo.getCode()).thenReturn("NotBlank");
    when(fieldErrorThree.getCode()).thenReturn("NotBlank");
    when(exceptionHandlerUtility.createErrorMessageForMissingFields(fieldErrors))
      .thenReturn("Username, password and email are required.");

    response = restControllerExceptionHandler.handleMissingRequestFields(methodArgumentNotValidException);
    errorMessage = "Username, password and email are required.";
    receivedMessage = response.getBody().getMessage();
    expectedStatus = HttpStatus.BAD_REQUEST;
    receivedStatus = response.getStatusCode();

    assertEquals(errorMessage, receivedMessage);
    assertEquals(expectedStatus, receivedStatus);
  }

  @Test
  public void handleMissingRequestFields_should_returnCorrectMessage_when_sizeConstraintValidationFails() {
    when(methodArgumentNotValidException.getBindingResult()).thenReturn(bindingResult);
    when(methodArgumentNotValidException.getBindingResult().getFieldErrors()).thenReturn(fieldErrors);
    when(fieldError.getCode()).thenReturn("Size");
    when(fieldError.getDefaultMessage()).thenReturn("Password must have 8 characters.");

    response = restControllerExceptionHandler.handleMissingRequestFields(methodArgumentNotValidException);
    errorMessage = "Password must have 8 characters.";
    receivedMessage = response.getBody().getMessage();
    expectedStatus = HttpStatus.BAD_REQUEST;
    receivedStatus = response.getStatusCode();

    assertEquals(errorMessage, receivedMessage);
    assertEquals(expectedStatus, receivedStatus);
  }

  @Test
  public void handleMissingRequestBody_should_returnCorrectMessage() {
    response = restControllerExceptionHandler.handleMissingRequestBody();
    errorMessage = "Request body is empty.";
    receivedMessage = response.getBody().getMessage();
    expectedStatus = HttpStatus.BAD_REQUEST;
    receivedStatus = response.getStatusCode();

    assertEquals(errorMessage, receivedMessage);
    assertEquals(expectedStatus, receivedStatus);
  }

  @Test
  public void handleIncorrectLoginDetails_should_returnCorrectMessage() {
    response = restControllerExceptionHandler.handleIncorrectLoginDetails();
    errorMessage = UserNotFoundException.MESSAGE;
    receivedMessage = response.getBody().getMessage();
    expectedStatus = HttpStatus.UNAUTHORIZED;
    receivedStatus = response.getStatusCode();

    assertEquals(errorMessage, receivedMessage);
    assertEquals(expectedStatus, receivedStatus);
  }

  @Test
  public void handleIdNotFound_should_returnCorrectMessage() {
    response = restControllerExceptionHandler.handleIdNotFound();
    errorMessage = IdNotFoundException.MESSAGE;
    receivedMessage = response.getBody().getMessage();
    expectedStatus = HttpStatus.NOT_FOUND;
    receivedStatus = response.getStatusCode();

    assertEquals(errorMessage, receivedMessage);
    assertEquals(expectedStatus, receivedStatus);
  }

  @Test
  public void handleForbiddenAction_should_returnCorrectMessage() {
    response = restControllerExceptionHandler.handleForbiddenAction();
    errorMessage = ForbiddenActionException.MESSAGE;
    receivedMessage = response.getBody().getMessage();
    expectedStatus = HttpStatus.FORBIDDEN;
    receivedStatus = response.getStatusCode();

    assertEquals(errorMessage, receivedMessage);
    assertEquals(expectedStatus, receivedStatus);
  }

}