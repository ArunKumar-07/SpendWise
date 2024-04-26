package com.project.expensetracker.repository;

import com.project.expensetracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ExpensesRepository extends JpaRepository<Expense,Long> {

    @Query("SELECT e FROM Expense e WHERE e.userId.id = :userId")
    List<Expense> findByUserId(@Param("userId") Long userId);

    //@Query("SELECT e FROM Expense e WHERE e.userId.id = :userId")
    @Query("SELECT e FROM Expense e JOIN e.userId u WHERE u.id = :userId AND e.Category = :category")
    List<Expense> findByUserIdAndCategory(@Param("userId") Long userId, String category);



}
