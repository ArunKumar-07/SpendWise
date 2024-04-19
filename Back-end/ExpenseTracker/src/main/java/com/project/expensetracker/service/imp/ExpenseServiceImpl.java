package com.project.expensetracker.service.imp;

import com.project.expensetracker.dto.ExpenseDto;
import com.project.expensetracker.dto.UserDetailsDto;
import com.project.expensetracker.entity.Expense;
import com.project.expensetracker.entity.UserDetails;
import com.project.expensetracker.exception.ResourceNotFoundException;
import com.project.expensetracker.repository.ExpensesRepository;
import com.project.expensetracker.service.ExpenseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ExpenseServiceImpl implements ExpenseService {

    private ExpensesRepository expensesRepository;
    private ModelMapper modelMapper;
    @Override
    public ExpenseDto createExpense(ExpenseDto expenseDTO) {
        Expense expense = modelMapper.map(expenseDTO, Expense.class);
        expense = expensesRepository.save(expense);
        return modelMapper.map(expense, ExpenseDto.class);
    }

    @Override
    public ExpenseDto updateExpense(Long id, ExpenseDto expenseDTO) {
        Optional<Expense> optionalExpense = expensesRepository.findById(id);
        if (optionalExpense.isPresent()) {
            Expense existingExpense = optionalExpense.get();
            BeanUtils.copyProperties(expenseDTO, existingExpense);
            existingExpense.setId(id);
            existingExpense = expensesRepository.save(existingExpense);
            return modelMapper.map(existingExpense, ExpenseDto.class);
        }
        throw new ResourceNotFoundException("Expense not found with id: " + id);
    }

    @Override
    public void deleteExpense(Long id) {
        if (expensesRepository.existsById(id)) {
            expensesRepository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Expense not found with id: " + id);
        }
    }

    @Override
    public ExpenseDto getExpenseById(Long id) {
        Optional<Expense> optionalExpense = expensesRepository.findById(id);
        if (optionalExpense.isPresent()) {
            return modelMapper.map(optionalExpense.get(),ExpenseDto.class);
        }
        throw new ResourceNotFoundException("Expense not found with id: " + id);
    }

    @Override
    public List<ExpenseDto> getAllExpenses() {
        List<Expense> expenses = expensesRepository.findAll();
        return expenses.stream()
                .map(expense -> modelMapper.map(expense, ExpenseDto.class))
                .collect(Collectors.toList());
    }
}
