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
    private String username;
    private String email;
    private String password;
    private Double balance;

//    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
//    private List<Transcation> transcation;
//    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
//    private List<Income> income;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
