package com.ffcontrol.fast_food_control.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ffcontrol.fast_food_control.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Boolean existsByUserName(String userName);
    Optional<User> findByUserName(String userName);
}
