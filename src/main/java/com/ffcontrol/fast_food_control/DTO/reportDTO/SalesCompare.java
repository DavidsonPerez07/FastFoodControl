package com.ffcontrol.fast_food_control.DTO.reportDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalesCompare {
    Double totalPeriod1;
    Double totalPeriod2;
    Double diff;
    Double growthPercentage;
}
