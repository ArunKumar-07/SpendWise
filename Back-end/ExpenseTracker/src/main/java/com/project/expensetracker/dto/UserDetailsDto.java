package com.project.expensetracker.dto;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private  Double balance;
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Transcation> transcation;
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    private List<Income> incomes;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
