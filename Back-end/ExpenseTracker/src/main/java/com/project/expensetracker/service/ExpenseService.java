package com.project.expensetracker.service;

import com.project.expensetracker.dto.ExpenseDto;

import java.util.List;

public interface ExpenseService {

    ExpenseDto createExpense(ExpenseDto expenseDTO);
    ExpenseDto updateExpense(Long id, ExpenseDto expenseDTO);
    void deleteExpense(Long id);
    ExpenseDto getExpenseById(Long id);
    List<ExpenseDto> getAllExpenses();
}
