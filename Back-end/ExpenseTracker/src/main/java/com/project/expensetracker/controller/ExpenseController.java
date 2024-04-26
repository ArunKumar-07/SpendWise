package com.project.expensetracker.controller;

import com.project.expensetracker.dto.ExpenseDto;
import com.project.expensetracker.entity.Expense;
import com.project.expensetracker.service.ExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;
    

    @PostMapping("create")
    public ResponseEntity<ExpenseDto> createExpense( @RequestBody ExpenseDto expenseDto) {
        Long userId = expenseDto.getUserId();
        ExpenseDto createdExpense = expenseService.createExpense(expenseDto, userId);
        return ResponseEntity.ok(createdExpense);
    }

    @GetMapping("/all/{id}")
    public ResponseEntity<List<ExpenseDto>> getAllExpenseById( @PathVariable("id") String userId) {
        List<ExpenseDto> expenses = expenseService.getAllExpenseById(userId);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<ExpenseDto>> getByCategory(@PathVariable("id") Long id){
         List<ExpenseDto> categoryDiff =  expenseService.getCategory(id);
        return ResponseEntity.ok(categoryDiff);
    }

}
