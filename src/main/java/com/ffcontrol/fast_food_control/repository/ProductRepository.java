package com.ffcontrol.fast_food_control.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ffcontrol.fast_food_control.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
    
}
