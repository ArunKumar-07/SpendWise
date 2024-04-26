package com.project.expensetracker.service.imp;

import com.project.expensetracker.dto.ExpenseDto;
import com.project.expensetracker.entity.Expense;
import com.project.expensetracker.entity.UserDetails;
import com.project.expensetracker.exception.ResourceNotFoundException;
import com.project.expensetracker.repository.ExpensesRepository;
import com.project.expensetracker.repository.UserDetailsRepository;
import com.project.expensetracker.service.ExpenseService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private ExpensesRepository expensesRepository;
    private final ModelMapper modelMapper;
    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    public ExpenseServiceImpl(ExpensesRepository expensesRepository, ModelMapper modelMapper) {
        this.expensesRepository = expensesRepository;
        this.modelMapper = modelMapper;
    }

        @Override
        public ExpenseDto createExpense(ExpenseDto expenseDTO, Long userId) {
            UserDetails userDetails = userDetailsRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("UserDetails not found with id: " + userId));
            Expense expense = modelMapper.map(expenseDTO, Expense.class);
            expense.getUserId(userDetails);
            try {
                expense = expensesRepository.save(expense);
            } catch (Exception e) {
                throw new RuntimeException("Failed to create expense: " + e.getMessage());
            }
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



//    @Override
//    public ExpenseDto getExpenseById(Long id) {
//        Optional<Expense> optionalExpense = expensesRepository.findById(id);
//        if (optionalExpense.isPresent()) {
//            return modelMapper.map(optionalExpense.get(), ExpenseDto.class);
//        }
//        throw new ResourceNotFoundException("Expense not found with id: " + id);
//    }



    @Override
    public List<ExpenseDto> getAllExpenseById(String currentUserIdentifier) {
        List<Expense> expenses = expensesRepository.findByUserId(Long.valueOf(currentUserIdentifier));
        return expenses.stream()
                .map(expense -> modelMapper.map(expense, ExpenseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ExpenseDto> getCategory(Long id){
        List<Expense>  Category = expensesRepository.findByUserIdAndCategory(id,"Income");
        return Category.stream()
                .map(expense -> modelMapper.map(expense, ExpenseDto.class))
                .collect(Collectors.toList());
    }
}
