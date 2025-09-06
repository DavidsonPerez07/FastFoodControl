package com.ffcontrol.fast_food_control.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ffcontrol.fast_food_control.entity.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long>{
    
}
