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
public class ExpenseDto {

    private Long id;
    private Long amount;
    private Date date;
    private String Category;
    private String Remarks;
    private UserDetails userDetails;
}
