package com.example.masterwork.user.services;

import com.example.masterwork.exceptions.ForbiddenActionException;
import com.example.masterwork.exceptions.UserNotFoundException;
import com.example.masterwork.order.models.Order;
import com.example.masterwork.registration.service.RegistrationServiceImpl;
import com.example.masterwork.security.password.PasswordService;
import com.example.masterwork.user.models.User;
import com.example.masterwork.user.models.UserDTO;
import com.example.masterwork.user.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

  @Mock
  private UserRepository mockUserRepository;

  @Mock
  private PasswordService mockPasswordService;

  private UserService userService;

  @Before
  public void setUp() {
    MockitoAnnotations.openMocks(this);
    userService = Mockito.spy(new UserServiceImpl(mockUserRepository, mockPasswordService));
  }

  @Test
  public void saveUser_validInput_shouldSaveUser() {
    User user = new User(1, "Szabo-Temple", "Oliver", "Oli",
            "oli@gmail.com", "password", "1234567890", "budakeszi",
            new ArrayList<>());
    when(mockUserRepository.save(user)).thenReturn(user);

    User result = userService.saveUser(user);

    assertEquals(user, result);

    verify(userService, Mockito.times(1)).saveUser(Mockito.any(User.class));
  }

  @Test(expected = UserNotFoundException.class)
  public void getByUsername_usernameNotExist_shouldThrowException() throws UserNotFoundException {
    String username = "Oli";
    when(mockUserRepository.findByUserName(username)).thenReturn(Optional.empty());

    userService.getByUsername(username);
  }

  @Test
  public void getByUsername_usernameExist_shouldReturnUser() throws UserNotFoundException {
    String username = "Oli";
    User user = new User(1, "Szabo-Temple", "Oliver", "Oli",
            "oli@gmail.com", "password", "1234567890", "budakeszi",
            new ArrayList<>());
    when(mockUserRepository.findByUserName(username)).thenReturn(Optional.of(user));

    User result = userService.getByUsername(username);

    assertEquals(user, result);
  }

  @Test(expected = UserNotFoundException.class)
  public void getById_userNotExist_shouldThrowException() throws UserNotFoundException {
    Integer userId = 1;
    when(mockUserRepository.findById(userId)).thenReturn(Optional.empty());

    userService.getById(userId);
  }

  @Test
  public void getById_userExist_shouldReturnUser() throws UserNotFoundException {
    Integer userId = 1;
    User user = new User(1, "Szabo-Temple", "Oliver", "Oli",
            "oli@gmail.com", "password", "1234567890", "budakeszi",
            new ArrayList<>());
    user.setId(userId);
    when(mockUserRepository.findById(userId)).thenReturn(Optional.of(user));

    User result = userService.getById(userId);

    assertEquals(user, result);
  }

  @Test(expected = UserNotFoundException.class)
  public void getByEmail_emailNotExist_shouldThrowException() throws UserNotFoundException {
    String email = "oli@gmail.com";
    when(mockUserRepository.findByEmail(email)).thenReturn(Optional.empty());

    userService.getByEmail(email);
  }

  @Test
  public void getByEmail_emailExist_shouldReturnUser() throws UserNotFoundException {
    String email = "oli@gmail.com";
    User user = new User(1, "Szabo-Temple", "Oliver", "Oli",
            "oli@gmail.com", "password", "1234567890", "budakeszi",
            new ArrayList<>());
    when(mockUserRepository.findByEmail(email)).thenReturn(Optional.of(user));

    User result = userService.getByEmail(email);

    assertEquals(user, result);
  }

  @Test
  public void convertUserToDTO_validInput_shouldReturnUserDTO() {
    User user = new User(1, "Szabo-Temple", "Oliver", "Oli",
            "oli@gmail.com", "password", "1234567890", "budakeszi",
            new ArrayList<>());
    UserDTO expected = new UserDTO(1, "Szabo-Temple Oliver", "Oli",
            "oli@gmail.com", "1234567890", "budakeszi");

    UserDTO result = userService.convertUserToDTO(user);

    assertEquals(expected, result);
  }

  @Test(expected = ForbiddenActionException.class)
  public void isUserIdMatching_nullUserId_shouldThrowException() {
    // Arrange
    Order order = new Order();
    User user = new User();
    user.setId(1);
    order.setUser(user);

    // Act
    userService.isUserIdMatching(null, order);
  }

  @Test(expected = ForbiddenActionException.class)
  public void isUserIdMatching_userIdNotMatching_shouldThrowException() {
    Integer userId = 2;
    Order order = new Order();
    User user = new User();
    user.setId(1);
    order.setUser(user);

    userService.isUserIdMatching(userId, order);
  }

  @Test
  public void isUserIdMatching_userIdMatching_shouldReturnTrue() {
    Integer userId = 1;
    Order order = new Order();
    User user = new User();
    user.setId(1);
    order.setUser(user);

    Boolean result = userService.isUserIdMatching(userId, order);

    assertTrue(result);
  }

}