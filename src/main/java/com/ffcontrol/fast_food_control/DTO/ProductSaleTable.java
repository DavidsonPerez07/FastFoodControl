package com.ffcontrol.fast_food_control.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSaleTable {
    private Long productId;
    private String productName;
    private Double salePrice;
}
