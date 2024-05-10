package com.project.expensetracker.repository;

import com.project.expensetracker.entity.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserInformationRepository extends JpaRepository<UserInformation,Long> {
    UserInformation findByUsername(String username);
}
