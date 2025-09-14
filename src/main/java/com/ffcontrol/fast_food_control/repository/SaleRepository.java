package com.ffcontrol.fast_food_control.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ffcontrol.fast_food_control.entity.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long>{
    List<Sale> findAllByOrderBySaleDateDesc();

    List<Sale> findBySaleDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
