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

   // @Autowired
    private UserDetailsRepository userDetailsRepository;
  //  @Autowired
     private ModelMapper modelMapper;

    @Override
    public UserDetailsDto createUser(UserDetailsDto userDetailsDto) {
        UserDetails userDetails = modelMapper.map(userDetailsDto,UserDetails.class);
        userDetails = userDetailsRepository.save(userDetails);
        return  modelMapper.map(userDetails,UserDetailsDto.class);
    }

    @Override
    public UserDetailsDto updateUser(Long id, UserDetailsDto userDetailsDto) {
        Optional<UserDetails> optionalUser = userDetailsRepository.findById(id);
        if (optionalUser.isPresent()) {
            UserDetails existingUser = optionalUser.get();

            // Update non-null properties from userDTO to existingUser
            if (userDetailsDto.getUsername() != null) {
                existingUser.setUsername(userDetailsDto.getUsername());
            }
            if (userDetailsDto.getEmail() != null) {
                existingUser.setEmail(userDetailsDto.getEmail());
            }
            // Add more properties as needed

            // Save the updated user
            existingUser = userDetailsRepository.save(existingUser);

            // Map the updated user to UserDTO
            return modelMapper.map(existingUser, UserDetailsDto.class);
        }
        throw new ResourceNotFoundException("User not found with id: " + id);
    }

    @Override
    public void deleteUser(Long id) {
        if(userDetailsRepository.existsById(id)){
            userDetailsRepository.deleteById(id);
        }else {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
    }

    @Override
    public UserDetailsDto getUserById(Long id, UserDetailsDto userDetailsDto) {
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
