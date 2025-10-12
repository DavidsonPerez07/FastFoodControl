package com.ffcontrol.fast_food_control.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ffcontrol.fast_food_control.entity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long>{
    List<Expense> findAllByOrderByExpenseDateDesc();

    List<Expense> findByExpenseDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
