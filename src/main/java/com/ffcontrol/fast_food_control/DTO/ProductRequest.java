package com.ffcontrol.fast_food_control.DTO;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String productName;

    private List<Ingredient> ingredients;

    private List<DirectCost> directCosts;

    private Double labour;

    private Double profitMargin;

    private MultipartFile image;
}
