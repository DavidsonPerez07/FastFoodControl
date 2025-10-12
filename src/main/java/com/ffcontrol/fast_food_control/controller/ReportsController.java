package com.ffcontrol.fast_food_control.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ffcontrol.fast_food_control.DTO.TopProduct;
import com.ffcontrol.fast_food_control.service.ReportsService;

@RestController
@RequestMapping("/reports")
public class ReportsController {
    @Autowired
    private ReportsService reportsService;

    @GetMapping("/top-products")
    public ResponseEntity<List<TopProduct>> getTopProducts(@RequestParam LocalDateTime startDate, 
                                                            @RequestParam LocalDateTime endDate, 
                                                            @RequestParam int topNumber) {
        return ResponseEntity.ok(reportsService.getTopBestSellingProducts(startDate, endDate, topNumber));
    }
}
