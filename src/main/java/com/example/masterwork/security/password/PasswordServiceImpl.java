package com.example.masterwork.security.password;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl implements PasswordService {

  @Override
  public String passwordEncoding(String password) {
    return BCrypt.hashpw(password, BCrypt.gensalt());
  }

  @Override
  public boolean isPasswordMatch(String password, String encodedPassword) {
    return BCrypt.checkpw(password, encodedPassword);
  }

}
