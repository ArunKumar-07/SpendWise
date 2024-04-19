package com.project.expensetracker.dto;

import com.project.expensetracker.entity.Expense;
import com.project.expensetracker.entity.Income;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto {
    private Long id;
    private String name;
    private String email;
    private String password;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Expense> expenses;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Income> incomes;
    public String getUsername() {
        return name;
    }

    public void setUsername(String name) {
        // Logic to set the username
        this.name = name; // Assuming 'name' represents the username
    }

}
