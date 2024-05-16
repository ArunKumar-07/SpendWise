package com.project.expensetracker.dto;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInformationDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private  Double balance;
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public void setBalance(Double balance) {
//        this.balance = balance;
//    }
//
}
