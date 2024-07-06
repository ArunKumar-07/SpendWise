package com.project.expensetracker.entity;
import javax.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "expenses")
@Getter
@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transcation {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    @Column(columnDefinition = "DATE")
    private LocalDate date;
    private String category;
    private String statement;
    private String modeOfPayment;
    private String Remarks;
    private Double currentBalance;
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserInformation userId;

    public UserInformation getUserId(UserInformation userId) {
        return userId;
    }

}
