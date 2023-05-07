package com.example.masterwork.security;

import com.example.masterwork.security.password.PasswordServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCrypt;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;


@RunWith(MockitoJUnitRunner.class)
public class PasswordServiceImplTest {

  private PasswordServiceImpl passwordService;

  @Before
  public void setUp() throws Exception {
    passwordService = new PasswordServiceImpl();
  }

  @Test
  public void passwordEncoding_usesBCryptProperly_when_passwordIsGiven() {
    String password = "password";
    String encodedPassword = "encodedPassword";
    try (MockedStatic<BCrypt> mockedBCrypt = mockStatic(BCrypt.class)) {
      mockedBCrypt.when(BCrypt::gensalt).thenReturn("");
      mockedBCrypt.when(() -> BCrypt.hashpw(anyString(), anyString())).thenReturn(encodedPassword);

      String result = passwordService.passwordEncoding(password);

      assertEquals(encodedPassword, result);
      mockedBCrypt.verify(times(1), BCrypt::gensalt);
      mockedBCrypt.verify(times(1), () -> BCrypt.hashpw(anyString(), anyString()));
    }
  }

  @Test
  public void passwordEncoding_changesThePassword_when_passwordIsGiven() {
    String password = "password";
    try (MockedStatic<BCrypt> mockedBCrypt = mockStatic(BCrypt.class)) {

      String result = passwordService.passwordEncoding(password);

      assertNotEquals(password, result);
    }
  }

  @Test
  public void isPasswordMatch_returnsTrue_when_BCyptCheckpwReturnsTrue() {
    String password = "password";
    String encodedPassword = "encodedPassword";
    try (MockedStatic<BCrypt> mockedBCrypt = mockStatic(BCrypt.class)) {
      mockedBCrypt.when(() -> BCrypt.checkpw(anyString(), anyString())).thenReturn(true);

      boolean result = passwordService.isPasswordMatch(password, encodedPassword);

      assertTrue(result);
    }
  }

  @Test
  public void isPasswordMatch_returnsFalse_when_BCyptCheckpwReturnsFalse() {
    String password = "password";
    String encodedPassword = "encodedPassword";
    try (MockedStatic<BCrypt> mockedBCrypt = mockStatic(BCrypt.class)) {
      mockedBCrypt.when(() -> BCrypt.checkpw(anyString(), anyString())).thenReturn(false);

      boolean result = passwordService.isPasswordMatch(password, encodedPassword);

      assertFalse(result);
    }
  }

}
