package com.project.expensetracker.entity;
import javax.persistence.*;
import lombok.*;
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
    private Date date;
    private String Category;
    private String source;
    private String Remarks;
    private Double currentBalance;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserInformation userId;

    public UserInformation getUserId(UserInformation userId) {
        return userId;
    }

    public void setUserId(UserInformation userId) {
        this.userId = userId;
    }

}
