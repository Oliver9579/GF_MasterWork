package com.example.masterwork.registration.service;

import com.example.masterwork.errorhandling.ErrorMessage;
import com.example.masterwork.exceptions.AlreadyTakenException;
import com.example.masterwork.exceptions.UserNotFoundException;
import com.example.masterwork.registration.dtos.RegistrationDTO;
import com.example.masterwork.security.password.PasswordService;
import com.example.masterwork.user.models.User;
import com.example.masterwork.user.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServiceImplTest {
  @Mock
  private UserService mockUserService;
  @Mock
  private PasswordService mockPasswordService;

  private RegistrationServiceImpl registrationService;
  private RegistrationDTO registrationDTO = new RegistrationDTO("Szabó-Temple", "Olivér", "Oli",
          "oli2001@gmail.com", "password", "+361234567", "budakeszi");
  private User user = new User("Szabó-Temple", "Olivér", "Oli", "oli2001@gmail.com",
          "encodedPassword", "+361234567", "budakeszi");

  @Before
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    registrationService = Mockito.spy(new RegistrationServiceImpl(mockUserService, mockPasswordService));
  }

  @Test
  public void testRegisterUserSuccess() {
    when(mockUserService.getByUsername(Mockito.anyString())).thenThrow(new UserNotFoundException());
    when(mockUserService.getByEmail(Mockito.anyString())).thenThrow(new UserNotFoundException());
    when(mockPasswordService.passwordEncoding(Mockito.anyString())).thenReturn("encodedPassword");
    when(mockUserService.saveUser(Mockito.any(User.class))).thenReturn(user);
    User registeredUser = registrationService.register(registrationDTO);
    assertEquals(user, registeredUser);
    verify(mockUserService, Mockito.times(1)).saveUser(Mockito.any(User.class));
    verify(mockUserService, Mockito.times(1)).getByUsername(Mockito.anyString());
    verify(mockUserService, Mockito.times(1)).getByEmail(Mockito.anyString());
  }

  @Test(expected = AlreadyTakenException.class)
  public void testRegisterUserWithTakenUsername() {
    doThrow(AlreadyTakenException.class).when(registrationService).validate(registrationDTO);
    registrationService.register(registrationDTO);
    verify(mockUserService, Mockito.times(0)).saveUser(Mockito.any(User.class));
  }

  @Test(expected = AlreadyTakenException.class)
  public void testRegisterUserWithTakenEmail() {
    doThrow(AlreadyTakenException.class).when(registrationService).validate(registrationDTO);
    registrationService.register(registrationDTO);
    verify(mockUserService, Mockito.times(0)).saveUser(Mockito.any(User.class));
  }

}
