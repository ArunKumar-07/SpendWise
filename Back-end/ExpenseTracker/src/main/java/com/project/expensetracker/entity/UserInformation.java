package com.project.expensetracker.entity;
import lombok.*;
import javax.persistence.*;


@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@Table(name = "userinformation")
public class UserInformation  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;
    private Double balance;

}
