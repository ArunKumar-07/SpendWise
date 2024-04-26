package com.project.expensetracker.repository;

import com.project.expensetracker.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails,Long> {
}
