package com.project.expensetracker.controller;

import com.project.expensetracker.dto.UserDetailsDto;
import com.project.expensetracker.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserDetailController {
    public UserDetailController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/createone")
    public ResponseEntity<UserDetailsDto> createUserDetails(@RequestBody UserDetailsDto userDetailsDto) {
        UserDetailsDto createdUserDetails = userDetailsService.createUser(userDetailsDto);
        return new ResponseEntity<>(createdUserDetails, HttpStatus.CREATED);
    }
}
