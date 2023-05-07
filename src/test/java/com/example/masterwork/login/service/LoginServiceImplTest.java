package com.example.masterwork.login.service;

import com.example.masterwork.exceptions.WrongPasswordException;
import com.example.masterwork.login.models.LoginDTO;
import com.example.masterwork.login.models.TokenDTO;
import com.example.masterwork.login.services.LoginService;
import com.example.masterwork.login.services.LoginServiceImpl;
import com.example.masterwork.security.config.JwtTokenUtil;
import com.example.masterwork.security.password.PasswordService;
import com.example.masterwork.user.models.User;
import com.example.masterwork.user.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class LoginServiceImplTest {

  private LoginService loginService;
  @Mock
  private UserService mockUserService;
  @Mock
  private PasswordService mockPasswordService;
  @Mock
  private JwtTokenUtil mockJwtTokenUtil;

  private LoginDTO loginDTO;

  @Before
  public void setup() {
    MockitoAnnotations.openMocks(this);
    this.loginService = new LoginServiceImpl(mockUserService, mockPasswordService, mockJwtTokenUtil);
    loginDTO = new LoginDTO("Oli2", "password");
    ReflectionTestUtils.setField(loginService, "expirationTime", 3_600_000L);
  }

  @Test(expected = NoSuchElementException.class)
  public void createTokenDtoResponse_should_throwNoSuchElementException_when_UserIsNotExist() {
    loginDTO = new LoginDTO("invalidUsername", "password");
    when(mockUserService.getByUsername(loginDTO.getUsername())).thenThrow(new NoSuchElementException());

    loginService.createTokenDtoResponse(loginDTO);
  }

  @Test(expected = WrongPasswordException.class)
  public void createTokenDtoResponse_should_throwWrongPasswordException_when_passwordIsIncorrect() {
    User user = new User();
    when(mockUserService.getByUsername(any())).thenReturn(user);
    when(mockPasswordService.isPasswordMatch(any(), any())).thenReturn(false);

    loginService.createTokenDtoResponse(loginDTO);
  }

  @Test
  public void createTokenDtoResponse_should_returnCorrectResponseEntity_when_ExistingUserIsGiven() {
    String password = "password";
    User user = new User(1, "Szabó-Temple", "Olivér", "Oli2", "oli2@gmail.com",
            "password", "+3630123456", "budakeszi");
    when(mockUserService.getByUsername(loginDTO.getUsername())).thenReturn(user);
    when(mockPasswordService.isPasswordMatch(loginDTO.getPassword(), password)).thenReturn(true);
    when(mockJwtTokenUtil.createJwtsToken(any())).thenReturn("token");

    TokenDTO response = loginService.createTokenDtoResponse(loginDTO);

    assertEquals("ok", response.getStatus());
    assertTrue(response.getToken() != null && !response.getToken().isEmpty());
  }



}
