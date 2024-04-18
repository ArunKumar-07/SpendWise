package com.project.expensetracker.service;

import com.project.expensetracker.dto.UserDetailsDto;

import java.util.List;

public interface UserDetailsService {
    UserDetailsDto createUser(UserDetailsDto userDetailsDto);
    UserDetailsDto updateUser(Long id , UserDetailsDto userDetailsDto);
    void deleteUser(Long id);
    UserDetailsDto getUserById(Long id , UserDetailsDto userDetailsDto);
    List<UserDetailsDto> getAllUser();
}
