package com.ffcontrol.fast_food_control.DTO;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleDTO {
    private Long saleId;

    private LocalDateTime saleDate;

    private String concept;

    private String paymentMethod;

    private Double totalPrice;

    private List<ProductDTO> products;
}
