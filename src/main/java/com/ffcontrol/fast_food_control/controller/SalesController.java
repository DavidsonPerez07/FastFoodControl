package com.ffcontrol.fast_food_control.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ffcontrol.fast_food_control.DTO.SaleDTO;
import com.ffcontrol.fast_food_control.DTO.SaleRequest;
import com.ffcontrol.fast_food_control.service.SalesService;

@RestController
@RequestMapping("/sales")
public class SalesController {
    @Autowired
    private SalesService salesService;

    @PostMapping("/register-sale")
    public ResponseEntity<SaleDTO> registerSale(@RequestBody SaleRequest request) {
        return ResponseEntity.ok(salesService.registerSale(request));
    }

    @GetMapping("/get-sale")
    public ResponseEntity<SaleDTO> getSaleById(@RequestParam Long saleId) {
        return ResponseEntity.ok(salesService.getSaleById(saleId));
    }

    @GetMapping("/get-all-sales")
    public ResponseEntity<List<SaleDTO>> getAllSales() {
        return ResponseEntity.ok(salesService.getAllSales());
    }

    @GetMapping("/get-sales-by-day")
    public ResponseEntity<List<SaleDTO>> getSalesByDay(@RequestParam LocalDate date) {
        return ResponseEntity.ok(salesService.getSalesByDay(date));
    }

    @GetMapping("/get-sales-by-date-range")
    public ResponseEntity<List<SaleDTO>> getSalesByDateRange(@RequestParam LocalDateTime startDate, 
                                                            @RequestParam LocalDateTime endDate) {
        return ResponseEntity.ok(salesService.getSalesByDateRange(startDate, endDate));
    }

    @DeleteMapping("/delete-sale")
    public ResponseEntity<String> deleteSale(@RequestParam Long saleId) {
        salesService.deleteSale(saleId);
        return ResponseEntity.ok("Sale deleted successfully");
    }
}
