package com.project.expensetracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "expenses")
@Getter
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
    private String Remarks;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserDetails userDetails;
}
