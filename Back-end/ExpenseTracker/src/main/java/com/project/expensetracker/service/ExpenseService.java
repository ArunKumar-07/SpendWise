package com.project.expensetracker.service;
import com.project.expensetracker.dto.ExpenseDto;
import com.project.expensetracker.entity.Expense;

import java.util.List;

public interface ExpenseService {

    ExpenseDto createExpense(ExpenseDto expenseDTO, Long userId);

    ExpenseDto updateExpense(Long id, ExpenseDto expenseDTO);
    void deleteExpense(Long id);
    //ExpenseDto getExpenseById(Long id);

    List<ExpenseDto> getAllExpenseById(String currentUserIdentifier);


    List<ExpenseDto> getCategory(Long id);
}
