package com.project.expensetracker.service.imp;

import com.project.expensetracker.dto.UserInformationDto;
import com.project.expensetracker.entity.UserInformation;
import com.project.expensetracker.exception.ResourceNotFoundException;
import com.project.expensetracker.repository.UserInformationRepository;
import com.project.expensetracker.service.UserInformationService;
import com.project.expensetracker.util.Jwtutils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserInformationServiceImpl implements UserInformationService {

    private final UserInformationRepository userInformationRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Jwtutils jwtutils;

    public UserInformationServiceImpl(UserInformationRepository userInformationRepository, ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder, Jwtutils jwtutils) {
        this.userInformationRepository = userInformationRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder=passwordEncoder;
        this.jwtutils = jwtutils;
    }
    private final  ModelMapper modelMapper;

    @Override
    public void signup(UserInformationDto signupRequest) {
        UserInformation userInfo = modelMapper.map(signupRequest, UserInformation.class);
        userInfo.setBalance(0.0);
        String hashedPassword = passwordEncoder.encode(signupRequest.getPassword());
        userInfo.setPassword(hashedPassword);
        userInformationRepository.save(userInfo);
    }
    @Override
    public ResponseEntity<String> login(UserInformationDto loginRequest) {
        System.out.println("service class entry");
        UserInformation userInfo = userInformationRepository.findByEmail(loginRequest.getEmail());
        if (userInfo == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email not found");
        }
        if (!passwordEncoder.matches(loginRequest.getPassword(), userInfo.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");
        }
        Long userId = userInfo.getId();
        String token = jwtutils.generateJwt(userId, userInfo.getUsername());
        System.out.printf(token);
        return ResponseEntity.ok(token);
    }

@Override
public UserInformationDto updateUser(Long id, UserInformationDto userInformationDto) {
    Optional<UserInformation> optionalUser = userInformationRepository.findById(id);
    if (optionalUser.isPresent()) {
        UserInformation existingUser = optionalUser.get();

        if (userInformationDto.getUsername() != null) {
            existingUser.setUsername(userInformationDto.getUsername());
            System.out.println("Updated username: " + userInformationDto.getUsername());
        }
        if (userInformationDto.getEmail() != null) {
            existingUser.setEmail(userInformationDto.getEmail());
            System.out.println("Updated email: " + userInformationDto.getEmail());
        }
        if (userInformationDto.getPassword() != null) {
            existingUser.setPassword(userInformationDto.getPassword());
            System.out.println("Updated password: " + userInformationDto.getPassword());
        }
        if (userInformationDto.getBalance() != null) {
            existingUser.setBalance(userInformationDto.getBalance());
            System.out.println("Updated balance: " + userInformationDto.getBalance());
        }

        UserInformation updatedUser = userInformationRepository.save(existingUser);

        return modelMapper.map(updatedUser, UserInformationDto.class);
    }
    throw new ResourceNotFoundException("User not found with id: " + id);
}

    @Override
    public UserInformationDto deleteUser(Long id) {
        if(userInformationRepository.existsById(id)){
            userInformationRepository.deleteById(id);
        }else {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        return null;
    }

    @Override
    public UserInformationDto getUserById(Long id) {
        Optional<UserInformation> optionalUser = userInformationRepository.findById(id);
        if (optionalUser.isPresent()) {
            return modelMapper.map(optionalUser.get(), UserInformationDto.class);
        }
        throw new ResourceNotFoundException("User not found with id: " + id);
    }

    @Override
    public List<UserInformationDto> getAllUser() {
        List<UserInformation> users  = userInformationRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserInformationDto.class))
                .collect(Collectors.toList());
    }
}
