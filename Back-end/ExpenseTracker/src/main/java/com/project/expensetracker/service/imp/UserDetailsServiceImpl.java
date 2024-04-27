package com.project.expensetracker.service.imp;

import com.project.expensetracker.dto.UserDetailsDto;
import com.project.expensetracker.entity.UserDetails;
import com.project.expensetracker.exception.ResourceNotFoundException;
import com.project.expensetracker.repository.UserDetailsRepository;
import com.project.expensetracker.service.UserDetailsService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    public UserDetailsServiceImpl(UserDetailsRepository userDetailsRepository, ModelMapper modelMapper) {
        this.userDetailsRepository = userDetailsRepository;
        this.modelMapper = modelMapper;
    }

     @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDetailsDto createUser(UserDetailsDto userDetailsDto) {
        UserDetails userDetails = modelMapper.map(userDetailsDto,UserDetails.class);
        userDetails.setBalance(0.0);
        userDetails = userDetailsRepository.save(userDetails);
        return  modelMapper.map(userDetails,UserDetailsDto.class);
    }

//    @Override
//    public UserDetailsDto updateUser(Long id, UserDetailsDto userDetailsDto) {
//        Optional<UserDetails> optionalUser = userDetailsRepository.findById(id);
//        if (optionalUser.isPresent()) {
//            UserDetails existingUser = optionalUser.get();
//
//            if (userDetailsDto.getUsername() != null) {
//                existingUser.setUsername(userDetailsDto.getUsername());
//            }
//            if (userDetailsDto.getEmail() != null) {
//                existingUser.setEmail(userDetailsDto.getEmail());
//            }
//            existingUser = userDetailsRepository.save(existingUser);
//            return modelMapper.map(existingUser, UserDetailsDto.class);
//        }
//        throw new ResourceNotFoundException("User not found with id: " + id);
//    }
@Override
public UserDetailsDto updateUser(Long id, UserDetailsDto userDetailsDto) {
    Optional<UserDetails> optionalUser = userDetailsRepository.findById(id);
    if (optionalUser.isPresent()) {
        UserDetails existingUser = optionalUser.get();

        // Log the existing user details
        System.out.println("Existing user details: " + existingUser);

        // Update the user details with non-null values from the userDetailsDto
        if (userDetailsDto.getUsername() != null) {
            existingUser.setUsername(userDetailsDto.getUsername());
            System.out.println("Updated username: " + userDetailsDto.getUsername());
        }
        if (userDetailsDto.getEmail() != null) {
            existingUser.setEmail(userDetailsDto.getEmail());
            System.out.println("Updated email: " + userDetailsDto.getEmail());
        }
        if (userDetailsDto.getPassword() != null) {
            existingUser.setPassword(userDetailsDto.getPassword());
            System.out.println("Updated password: " + userDetailsDto.getPassword());
        }
        if (userDetailsDto.getBalance() != null) {
            existingUser.setBalance(userDetailsDto.getBalance());
            System.out.println("Updated balance: " + userDetailsDto.getBalance());
        }

        // Save the updated user details
        UserDetails updatedUser = userDetailsRepository.save(existingUser);

        // Log the updated user details
        System.out.println("Updated user details: " + updatedUser);

        // Map the updated user details to UserDetailsDto and return
        return modelMapper.map(updatedUser, UserDetailsDto.class);
    }
    throw new ResourceNotFoundException("User not found with id: " + id);
}

    @Override
    public UserDetailsDto deleteUser(Long id) {
        if(userDetailsRepository.existsById(id)){
            userDetailsRepository.deleteById(id);
        }else {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        return null;
    }

    @Override
    public UserDetailsDto getUserById(Long id) {
        Optional<UserDetails> optionalUser = userDetailsRepository.findById(id);
        if (optionalUser.isPresent()) {
            return modelMapper.map(optionalUser.get(), UserDetailsDto.class);
        }
        throw new ResourceNotFoundException("User not found with id: " + id);
    }

    @Override
    public List<UserDetailsDto> getAllUser() {
        List<UserDetails> users  = userDetailsRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDetailsDto.class))
                .collect(Collectors.toList());
    }
}
