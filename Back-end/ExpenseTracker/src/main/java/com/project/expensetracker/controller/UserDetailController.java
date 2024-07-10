package com.project.expensetracker.controller;

import com.project.expensetracker.dto.UserInformationDto;
import com.project.expensetracker.service.UserInformationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
        ResponseEntity<String> response =   userInformationService.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/current")
    public ResponseEntity<UserInformationDto> getUserById(HttpServletRequest request){
        Long userId = (Long) request.getAttribute("userId");
        UserInformationDto userInformationDtoGet = userInformationService.getUserById(userId);
        return ResponseEntity.ok(userInformationDtoGet);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUserById(HttpServletRequest request){
        Long userId = (Long) request.getAttribute("userId");
        userInformationService.deleteUser(userId);
        return  ResponseEntity.ok("Successfully deleted the user");
    }

    @PutMapping("/update")
    public ResponseEntity<UserInformationDto> updateUserInfo(HttpServletRequest request, @RequestBody UserInformationDto userInformationDto){
        Long userId = (Long) request.getAttribute("userId");
        UserInformationDto userDetailsDtoUpdateUser = userInformationService.updateUser(userId, userInformationDto);
        return ResponseEntity.ok(userDetailsDtoUpdateUser);
    }
}
