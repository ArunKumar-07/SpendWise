package com.project.expensetracker.controller;

import com.project.expensetracker.dto.TranscationDto;
import com.project.expensetracker.service.TranscationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/new")
@AllArgsConstructor
public class TranscationController {
    private TranscationService transcationService;

    @PostMapping("create/{type}")
    public ResponseEntity<TranscationDto> createExpense(HttpServletRequest request,@RequestBody TranscationDto transcationDto, @PathVariable("type") String type) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            throw new IllegalArgumentException("User ID must not be null");
        }
        TranscationDto createdExpense = transcationService.createExpense(transcationDto, userId, type);
        return ResponseEntity.ok(createdExpense);
    }
    @GetMapping("/all")
    public ResponseEntity<List<TranscationDto>> getAllExpenseById(HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        List<TranscationDto> expenses = transcationService.getAllExpenseById(userId.toString());
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/get/{statement}")
    public ResponseEntity<?> getByCategory(HttpServletRequest request ,@PathVariable("statement") String statement) {
        Long userId = (Long) request.getAttribute("userId");
        List<TranscationDto> statementDiff = transcationService.getStatement(userId, statement);

        if (statementDiff.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found");
        }
        return ResponseEntity.ok(statementDiff);
    }

    @PutMapping("update/{type}/{id}")
    public ResponseEntity<TranscationDto> updateTranscation(@PathVariable Long id,@RequestBody TranscationDto transcationDto,@PathVariable("type") String type){
        TranscationDto updateTranscationDto = transcationService.updateExpense(id , transcationDto,type);
        return ResponseEntity.ok(updateTranscationDto);
    }

 @DeleteMapping("delete/{type}")
    public ResponseEntity<Void> deleteTranscation(HttpServletRequest request,@PathVariable("type") String type ){
     Long userId = (Long) request.getAttribute("userId");
        transcationService.deleteExpense(userId,type);
        return ResponseEntity.noContent().build();
    }
}
