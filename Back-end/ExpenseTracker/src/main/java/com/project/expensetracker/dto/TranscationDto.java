package com.project.expensetracker.dto;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TranscationDto {

    private Long id;
    private Double amount;
    private LocalDate date;
    private String statement;
    private String modeOfPayment;
    private String category;
    private String Remarks;
    private Double currentBalance;
    private Long userId;

}
