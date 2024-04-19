package com.project.expensetracker.dto;

import com.project.expensetracker.entity.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IncomeDto {
    private Long id;
    private Double amount;
    private String Remarks;
    private Date date;
    private UserDetails userDetails;
}
