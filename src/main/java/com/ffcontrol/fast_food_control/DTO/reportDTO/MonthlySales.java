package com.ffcontrol.fast_food_control.DTO.reportDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlySales {
    Integer year;
    Integer month;
    Double totalSales;
}
