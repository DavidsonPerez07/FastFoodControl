package com.ffcontrol.fast_food_control.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "expenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long expenseId;

    @Column(nullable = false)
    private LocalDateTime expenseDate;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String concept;

    @Column(nullable = false)
    private Double totalPrice;
}
