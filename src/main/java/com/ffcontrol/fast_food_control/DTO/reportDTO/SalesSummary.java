package com.ffcontrol.fast_food_control.DTO.reportDTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesSummary {
    LocalDate date;
    Double totalSales;
    Long totalOrders;
}
