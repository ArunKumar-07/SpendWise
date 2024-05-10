package com.project.expensetracker.service;

import com.project.expensetracker.dto.UserInformationDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserInformationService  {
   // UserInformationDto createUser(UserInformationDto userInformationDto);
    UserInformationDto updateUser(Long id , UserInformationDto userInformationDto);
    UserInformationDto deleteUser(Long id);
    UserInformationDto getUserById(Long id);
    List<UserInformationDto> getAllUser();
    void signup(UserInformationDto signupRequest);
    ResponseEntity<String> login(UserInformationDto loginRequest);

}

