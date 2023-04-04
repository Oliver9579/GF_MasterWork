package com.example.masterwork.registration.service;

import com.example.masterwork.exceptions.AlreadyTakenException;
import com.example.masterwork.registration.dtos.RegistrationDTO;
import com.example.masterwork.user.models.User;
import org.springframework.stereotype.Service;

@Service
public interface RegistrationService {

  void validate(RegistrationDTO rdto) throws AlreadyTakenException;

  User register(RegistrationDTO rdto) throws AlreadyTakenException;

}
