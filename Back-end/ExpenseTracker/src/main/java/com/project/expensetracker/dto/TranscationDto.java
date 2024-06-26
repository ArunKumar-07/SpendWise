package com.project.expensetracker.dto;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TranscationDto {

    private Long id;
    private Double amount;
    private Date date;
    private String Category;
    private String source;
    private String Remarks;
    private Double currentBalance;
    private Long userId;

}
