package com.project.expensetracker.service.imp;

import com.project.expensetracker.dto.TranscationDto;
import com.project.expensetracker.entity.Transcation;
import com.project.expensetracker.entity.UserInformation;
import com.project.expensetracker.exception.ResourceNotFoundException;
import com.project.expensetracker.repository.TranscationRepository;
import com.project.expensetracker.repository.UserInformationRepository;
import com.project.expensetracker.service.TranscationService;
import javax.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TranscationServiceImpl implements TranscationService {

    private final TranscationRepository transcationRepository;
    private final ModelMapper modelMapper;
    private UserInformationRepository userInformationRepository;

    @Autowired
    public TranscationServiceImpl(TranscationRepository transcationRepository, ModelMapper modelMapper) {
        this.transcationRepository = transcationRepository;
        this.modelMapper = modelMapper;
    }

        @Override
        public TranscationDto createExpense(TranscationDto transcationDTO, Long userId, String type) { 
            UserInformation userInformation = userInformationRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("UserDetails not found with id: " + userId));
            transcationDTO.setStatement(type);
            Transcation transcation = modelMapper.map(transcationDTO, Transcation.class);
            transcation.setUserId(userInformation);
           // transcation.getUserId(userInformation);

            Double currentBalance = userInformation.getBalance();
            if("expense".equalsIgnoreCase(type)){
                currentBalance -= transcationDTO.getAmount();
            }else if ("income".equalsIgnoreCase(type)){
                currentBalance += transcationDTO.getAmount();
            } else{
                throw new IllegalArgumentException("Invalid type: " + type);
            }

            transcation.setCurrentBalance(currentBalance);
            userInformation.setBalance(currentBalance);

            try {
                transcation = transcationRepository.save(transcation);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create expense: " + e.getMessage());
            }
            return modelMapper.map(transcation, TranscationDto.class);
        }

    @Override
    public TranscationDto updateExpense(Long id, TranscationDto transcationDTO, String type) {
        Optional<Transcation> optionalExpense = transcationRepository.findById(id);

        if (optionalExpense.isPresent()) {
            Transcation existingTranscation = optionalExpense.get();
            UserInformation userInformation = existingTranscation.getUserId();
            Double currentBalance = userInformation.getBalance();

            if ("expense".equalsIgnoreCase(type)) {
                currentBalance -= transcationDTO.getAmount() - existingTranscation.getAmount();
            } else if ("income".equalsIgnoreCase(type)) {
                currentBalance += transcationDTO.getAmount() - existingTranscation.getAmount();
            } else {
                throw new IllegalArgumentException("Invalid type: " + type);
            }
            userInformation.setBalance(currentBalance);

            BeanUtils.copyProperties(transcationDTO, existingTranscation);
            existingTranscation.setId(id);
            existingTranscation = transcationRepository.save(existingTranscation);
            return modelMapper.map(existingTranscation, TranscationDto.class);
        }
        throw new ResourceNotFoundException("Expense not found with id: " + id);
    }

    @Override
    public void deleteExpense(Long id,String type) {
        Optional<Transcation> optionalExpense = transcationRepository.findById(id);
        if (optionalExpense.isPresent()) {
            Transcation existingTranscation = optionalExpense.get();
            UserInformation userInformation = existingTranscation.getUserId();

            Double currentBalance = userInformation.getBalance();
            if ("expense".equalsIgnoreCase(type)) {
                currentBalance += existingTranscation.getAmount();
            } else if ("income".equalsIgnoreCase(type)) {
                currentBalance -= existingTranscation.getAmount();
            } else {
                throw new IllegalArgumentException("Invalid type: " + type);
            }
            userInformation.setBalance(currentBalance);

            transcationRepository.deleteById(id);
            userInformationRepository.save(userInformation);
        }else{
            throw new ResourceNotFoundException("Expense not found with id: " + id);
        }
    }

    @Override
    public List<TranscationDto> getAllExpenseById(String currentUserIdentifier) {
        List<Transcation> transcation = transcationRepository.findByUserId(Long.valueOf(currentUserIdentifier));
        return transcation.stream()
                .map(expense -> modelMapper.map(expense, TranscationDto.class))
                .collect(Collectors.toList());
    }

@Override
public List<TranscationDto> getStatement(Long id, String statement) {
    List<Transcation> category = transcationRepository.findByUserIdAndStatement(id, statement);

    if (id == null ||  statement.isEmpty()) {
        return Collections.emptyList();
    }
    return category.stream()
            .map(expense -> modelMapper.map(expense, TranscationDto.class))
            .collect(Collectors.toList());
    }
}
