package com.ffcontrol.fast_food_control.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ffcontrol.fast_food_control.DTO.reportDTO.MonthlySales;
import com.ffcontrol.fast_food_control.DTO.reportDTO.ProductSalesCompare;
import com.ffcontrol.fast_food_control.DTO.reportDTO.SalesCompare;
import com.ffcontrol.fast_food_control.DTO.reportDTO.SalesSummary;
import com.ffcontrol.fast_food_control.DTO.reportDTO.SalesSummaryProjection;
import com.ffcontrol.fast_food_control.DTO.reportDTO.TopProduct;
import com.ffcontrol.fast_food_control.service.ReportsService;

@RestController
@RequestMapping("/reports")
public class ReportsController {
    @Autowired
    private ReportsService reportsService;

    @GetMapping("/top-products")
    public ResponseEntity<List<TopProduct>> getTopProducts(@RequestParam LocalDate startDate, 
                                                            @RequestParam LocalDate endDate, 
                                                            @RequestParam int topNumber) {
        return ResponseEntity.ok(reportsService.getTopBestSellingProducts(startDate, endDate, topNumber));
    }

    @GetMapping("/sales-summary")
    public ResponseEntity<List<SalesSummaryProjection>> getSalesSummary(@RequestParam LocalDate startDate,
                                                            @RequestParam(required = false) LocalDate endDate) {
        return ResponseEntity.ok(reportsService.getSalesSummary(startDate, endDate));
    }

    @GetMapping("/monthly-sales")
    public ResponseEntity<List<MonthlySales>> getMonthlySales(
            @RequestParam LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate) {
        return ResponseEntity.ok(reportsService.getMonthlySales(startDate, endDate));
    }

    @GetMapping("/sales-growth")
    public ResponseEntity<List<Double>> getSalesGrowth(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(reportsService.getMonthlyGrowthRatios(startDate, endDate));
    }

    @GetMapping("/compare-sales")
    public ResponseEntity<SalesCompare> compareSales(
            @RequestParam LocalDateTime startDate1,
            @RequestParam LocalDateTime endDate1,
            @RequestParam LocalDateTime startDate2,
            @RequestParam LocalDateTime endDate2) {
        return ResponseEntity.ok(reportsService.compareSalesBetweenRanges(startDate1, endDate1, startDate2, endDate2));
    }

    @GetMapping("/product-sales-compare")
    public ResponseEntity<List<ProductSalesCompare>> compareProductSales(
            @RequestParam Long productId1,
            @RequestParam Long productId2) {
        return ResponseEntity.ok(reportsService.compareTwoProducts(productId1, productId2));
    }
}
