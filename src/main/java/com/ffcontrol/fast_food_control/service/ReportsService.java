package com.ffcontrol.fast_food_control.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffcontrol.fast_food_control.DTO.TopProduct;
import com.ffcontrol.fast_food_control.repository.ExpenseRepository;
import com.ffcontrol.fast_food_control.repository.ProductRepository;
import com.ffcontrol.fast_food_control.repository.SaleProductRepository;
import com.ffcontrol.fast_food_control.repository.SaleRepository;

@Service
public class ReportsService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private SaleProductRepository saleProductRepository;

    public List<TopProduct> getTopBestSellingProducts(LocalDateTime startDate, LocalDateTime endDate, int topNumber) {
        List<Map<String, Object>> results = saleProductRepository.findTopSellingProducts(startDate, endDate, topNumber);

        return results.stream()
                .map(row -> {
                    TopProduct topProduct = new TopProduct();
                    topProduct.setProductId(((Number) row.get("productId")).longValue());
                    topProduct.setProductName((String) row.get("productName"));
                    topProduct.setSalePrice(((Number) row.get("salePrice")).doubleValue());
                    topProduct.setTotalSold(((Number) row.get("totalSold")).intValue());
                    topProduct.setTotalSalesValue(((Number) row.get("totalSalesValue")).doubleValue());
                    return topProduct;
                })
                .toList();
    }
}
