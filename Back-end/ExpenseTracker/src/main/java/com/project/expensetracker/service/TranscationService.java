package com.project.expensetracker.service;
import com.project.expensetracker.dto.TranscationDto;

import java.util.List;

public interface TranscationService {

    TranscationDto createExpense(TranscationDto transcationDTO, Long userId, String type);
    TranscationDto updateExpense(Long id, TranscationDto transcationDTO, String type);
    void deleteExpense(Long id,String type);
    List<TranscationDto> getAllExpenseById(String currentUserIdentifier);
    List<TranscationDto> getStatement(Long id, String statement);
}
