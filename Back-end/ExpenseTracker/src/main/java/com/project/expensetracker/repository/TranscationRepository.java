package com.project.expensetracker.repository;

import com.project.expensetracker.entity.Transcation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TranscationRepository extends JpaRepository<Transcation,Long> {

    @Query("SELECT e FROM Transcation e WHERE e.userId.id = :userId")
    List<Transcation> findByUserId(@Param("userId") Long userId);


    @Query("SELECT e FROM Transcation e JOIN e.userId u WHERE u.id = :userId AND e.statement = :statement")
    List<Transcation> findByUserIdAndStatement(@Param("userId") Long userId, String statement);


}
