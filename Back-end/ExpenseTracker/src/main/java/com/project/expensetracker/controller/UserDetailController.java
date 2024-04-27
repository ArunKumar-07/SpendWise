package com.project.expensetracker.controller;

import com.project.expensetracker.dto.UserDetailsDto;
import com.project.expensetracker.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class UserDetailController {
    public UserDetailController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping("/newuser")
    public ResponseEntity<UserDetailsDto> createUserDetails(@RequestBody UserDetailsDto userDetailsDto) {
        UserDetailsDto createdUserDetails = userDetailsService.createUser(userDetailsDto);
        return ResponseEntity.ok(createdUserDetails);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailsDto> getUserById(@PathVariable("id")  Long id){
        UserDetailsDto userDetailsDtoGet = userDetailsService.getUserById(id);
        return ResponseEntity.ok(userDetailsDtoGet);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id){
        userDetailsService.deleteUser(id);
        return  ResponseEntity.ok("Successfully deleted the user");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDetailsDto> updateUserInfo(@PathVariable("id") Long id,@RequestBody UserDetailsDto userDetailsDto){
        UserDetailsDto userDetailsDtoUpdateUser = userDetailsService.updateUser(id,userDetailsDto);
        return ResponseEntity.ok(userDetailsDtoUpdateUser);
    }
}
