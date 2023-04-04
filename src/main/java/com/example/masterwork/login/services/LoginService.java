package com.example.masterwork.login.services;

import com.example.masterwork.exceptions.UserNotFoundException;
import com.example.masterwork.exceptions.WrongPasswordException;
import com.example.masterwork.login.models.LoginDTO;
import com.example.masterwork.login.models.TokenDTO;

public interface LoginService {

  TokenDTO createTokenDtoResponse(LoginDTO loginDTO) throws UserNotFoundException, WrongPasswordException;

}
