package com.project.expensetracker.dto;
import com.project.expensetracker.entity.UserDetails;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDto {

    private Long id;
    private Double amount;
    private Date date;
    private String Category;
    private String source;
    private String Remarks;
    private Long user_id;

    public Long getUserId() {
        return user_id;
    }

    public void setUserId(Long userId) {
        this.user_id = userId;
    }
}
