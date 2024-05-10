package com.project.expensetracker.controller;

import com.project.expensetracker.dto.UserInformationDto;
import com.project.expensetracker.service.UserInformationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/new")
public class UserDetailController {
    public UserDetailController(UserInformationService userInformationService) {
        this.userInformationService = userInformationService;

    }
    private final UserInformationService userInformationService;

@PostMapping("/signup")
public ResponseEntity<?> signup(@RequestBody UserInformationDto signupRequest) {
    userInformationService.signup(signupRequest);
    return ResponseEntity.ok("Signup successful");
}

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserInformationDto loginRequest) {
        userInformationService.login(loginRequest);
        return ResponseEntity.ok("login successful");
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserInformationDto> getUserById(@PathVariable("id")  Long id){
        UserInformationDto userInformationDtoGet = userInformationService.getUserById(id);
        return ResponseEntity.ok(userInformationDtoGet);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id){
        userInformationService.deleteUser(id);
        return  ResponseEntity.ok("Successfully deleted the user");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserInformationDto> updateUserInfo(@PathVariable("id") Long id, @RequestBody UserInformationDto userInformationDto){
        UserInformationDto userDetailsDtoUpdateUser = userInformationService.updateUser(id, userInformationDto);
        return ResponseEntity.ok(userDetailsDtoUpdateUser);
    }
}
