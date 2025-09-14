package com.ffcontrol.fast_food_control.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductQuantity {
    private ProductDTO product;
    private Integer quantity;
}
