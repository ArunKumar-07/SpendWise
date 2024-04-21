package com.project.expensetracker.controller;

import com.project.expensetracker.dto.ExpenseDto;
import com.project.expensetracker.service.ExpenseService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@AllArgsConstructor
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @PostMapping("create")
    public ResponseEntity<ExpenseDto> createExpense( ExpenseDto expenseDto){
        ExpenseDto crateExpenses = expenseService.createExpense(expenseDto);
        return ResponseEntity.ok(crateExpenses);
       // return ResponseEntity.status(HttpStatus.CREATED).body(crateExpenses);
    }

}
