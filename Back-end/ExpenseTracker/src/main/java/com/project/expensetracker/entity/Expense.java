package com.project.expensetracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "expenses")
@Getter
@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    private Date date;
    private String Category;
    private String source;
    private String Remarks;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserDetails userId;

//    public void setUserDetails(UserDetails userDetails) {
//        this.userDetails = userDetails;
//    }
public UserDetails getUserId(UserDetails userId) {
    return userId;
}

    public void setUserId(UserDetails userId) {
        this.userId = userId;
    }
}
