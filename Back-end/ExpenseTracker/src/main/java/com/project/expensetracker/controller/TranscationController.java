package com.project.expensetracker.controller;

import com.project.expensetracker.dto.TranscationDto;
import com.project.expensetracker.service.TranscationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/new")
@AllArgsConstructor
public class TranscationController {
    //@Autowired
    private TranscationService transcationService;

//    {
//        "userId":1,
//            "amount": 800000.0,
//            "date": "2024-04-17",
//            "source": "online",
//            "category": "EMI",
//            "remarks": "phone"
//    }

    @PostMapping("create/{type}")
    public ResponseEntity<TranscationDto> createExpense(@RequestBody TranscationDto transcationDto, @PathVariable("type") String type) {
        Long userId = transcationDto.getUserId();
        if (userId == null) {
            throw new IllegalArgumentException("User ID must not be null");
        }
        System.out.println("Received userId: " + userId);
        TranscationDto createdExpense = transcationService.createExpense(transcationDto, userId, type);
        return ResponseEntity.ok(createdExpense);
    }
    @GetMapping("/all/{id}")
    public ResponseEntity<List<TranscationDto>> getAllExpenseById(@PathVariable("id") String userId) {
        List<TranscationDto> expenses = transcationService.getAllExpenseById(userId);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/get/{statement}/{id}")
    public ResponseEntity<?> getByCategory(@PathVariable("id") Long id, @PathVariable("statement") String statement) {
        List<TranscationDto> statementDiff = transcationService.getStatement(id, statement);

        if (statementDiff.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found");
        }
        return ResponseEntity.ok(statementDiff);
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
