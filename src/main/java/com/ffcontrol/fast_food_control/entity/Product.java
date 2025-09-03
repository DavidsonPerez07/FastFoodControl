package com.ffcontrol.fast_food_control.entity;

import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @Column(nullable = false)
    private String productName;

    @ElementCollection
    @CollectionTable(
        name = "product_ingredients", 
        joinColumns = @JoinColumn(name = "product_id")
    )
    @Column(name = "ingredient", nullable = false)
    private List<String> ingredients;

    @Column(nullable = false)
    private Double netPrice;

    @Column(nullable = false)
    private Double profitMargin;

    @Column(nullable = false)
    private Double salePrice;
}
