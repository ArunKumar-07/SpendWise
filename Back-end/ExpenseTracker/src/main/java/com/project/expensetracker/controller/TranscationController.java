package com.project.expensetracker.controller;

import com.project.expensetracker.dto.TranscationDto;
import com.project.expensetracker.service.TranscationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app")
@AllArgsConstructor
public class TranscationController {
    //@Autowired
    private TranscationService transcationService;
    

    @PostMapping("create/{type}")
    public ResponseEntity<TranscationDto> createExpense(@RequestBody TranscationDto transcationDto, @PathVariable("type") String type) {
        Long userId = transcationDto.getUserId();
        TranscationDto createdExpense = transcationService.createExpense(transcationDto, userId,type);
        return ResponseEntity.ok(createdExpense);
    }


    @GetMapping("/all/{id}")
    public ResponseEntity<List<TranscationDto>> getAllExpenseById(@PathVariable("id") String userId) {
        List<TranscationDto> expenses = transcationService.getAllExpenseById(userId);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/get/{medium}/{id}")
    public ResponseEntity<?> getByCategory(@PathVariable("id") Long id, @PathVariable("medium") String medium) {
        List<TranscationDto> categoryDiff = transcationService.getCategory(id, medium);

        if (categoryDiff.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found");
        }

        return ResponseEntity.ok(categoryDiff);
    }

    @PutMapping("update/{id}/{type}")
    public ResponseEntity<TranscationDto> updateTranscation(@PathVariable("id") Long id ,@RequestBody TranscationDto transcationDto,@PathVariable("type") String type){
        TranscationDto updateTranscationDto = transcationService.updateExpense(id , transcationDto,type);
        return ResponseEntity.ok(updateTranscationDto);
    }

 @DeleteMapping("delete/{id}/{type}")
    public ResponseEntity<Void> deleteTranscation(@PathVariable("id") Long id,@PathVariable("type") String type ){
        transcationService.deleteExpense(id,type);
        return ResponseEntity.noContent().build();
    }



}
