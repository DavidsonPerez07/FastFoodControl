package com.ffcontrol.fast_food_control.DTO.reportDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSalesCompareResponse {
    private String productName;
    private Long totalUnitSold;
    private Double totalSalesValue;
    private Long unitsDiff;
    private Double salesValueDiff;
}
