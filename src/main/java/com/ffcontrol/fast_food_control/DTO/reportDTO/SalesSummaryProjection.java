package com.ffcontrol.fast_food_control.DTO.reportDTO;

import java.time.LocalDate;

public interface SalesSummaryProjection {
    LocalDate getDate();
    Double getTotalSales();
    Long getTotalOrders();
}
