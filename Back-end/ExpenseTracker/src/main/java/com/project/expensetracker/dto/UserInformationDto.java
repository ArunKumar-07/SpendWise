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

}
