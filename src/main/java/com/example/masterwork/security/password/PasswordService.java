package com.example.masterwork.security.password;

public interface PasswordService {

  String passwordEncoding(String password);

  boolean isPasswordMatch(String password, String encodedPassword);

}
