package com.ffcontrol.fast_food_control.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long productId;

    private String productName;

    private List<String> ingredients;

    private Double netPrice;

    private Double profitMargin;

    private Double salePrice;

    private String imageUrl;
}
