package com.example.masterwork.login.services;

import com.example.masterwork.exceptions.UserNotFoundException;
import com.example.masterwork.exceptions.WrongPasswordException;
import com.example.masterwork.login.models.LoginDTO;
import com.example.masterwork.login.models.TokenDTO;
import com.example.masterwork.security.password.PasswordService;
import com.example.masterwork.user.models.User;
import com.example.masterwork.user.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedHashMap;

@Service
public class LoginServiceImpl implements LoginService {

  private UserService userService;
  private PasswordService passwordService;
  private JwtTokenUtil jwtTokenUtil;
  @Value("${jwt.expiration.time}")
  private Long expirationTime;

  public LoginServiceImpl(UserService userService, PasswordService passwordService, JwtTokenUtil jwtTokenUtil) {
    this.userService = userService;
    this.passwordService = passwordService;
    this.jwtTokenUtil = jwtTokenUtil;
  }

  public TokenDTO createTokenDtoResponse(LoginDTO loginDTO) throws UserNotFoundException, WrongPasswordException {
    User user = userService.getByUsername(loginDTO.getUsername());
    if (!passwordService.isPasswordMatch(loginDTO.getPassword(), user.getPassword()))
      throw new WrongPasswordException();
    LinkedHashMap<String, Object> claims = new LinkedHashMap<>();
    claims.put("username", user.getUsername());
    claims.put("userId", user.getId());
    claims.put("exp", new Date(System.currentTimeMillis() + expirationTime));
    return new TokenDTO(jwtTokenUtil.createJwtsToken(claims));
  }

}
