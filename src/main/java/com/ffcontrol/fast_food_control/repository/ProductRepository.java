package com.ffcontrol.fast_food_control.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ffcontrol.fast_food_control.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
    
}
