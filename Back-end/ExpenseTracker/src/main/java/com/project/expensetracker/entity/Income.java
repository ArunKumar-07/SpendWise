package com.project.expensetracker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "income")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Income {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    private String Remarks;
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id" , nullable = false)
    private UserDetails userDetails;

}
