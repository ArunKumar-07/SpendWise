package com.project.expensetracker.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usersdetails")
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private List<Expense> expense;
    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private List<Income> income;

    public void setUsername(String name) {
        this.name = name;
    }
}
