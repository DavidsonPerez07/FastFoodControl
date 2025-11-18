package com.ffcontrol.fast_food_control.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffcontrol.fast_food_control.DTO.reportDTO.GrowMonthlyRatio;
import com.ffcontrol.fast_food_control.DTO.reportDTO.LessSoldProduct;
import com.ffcontrol.fast_food_control.DTO.reportDTO.MonthlySales;
import com.ffcontrol.fast_food_control.DTO.reportDTO.ProductSalesCompare;
import com.ffcontrol.fast_food_control.DTO.reportDTO.ProductSalesCompareResponse;
import com.ffcontrol.fast_food_control.DTO.reportDTO.SalesCompare;
import com.ffcontrol.fast_food_control.DTO.reportDTO.SalesSummary;
import com.ffcontrol.fast_food_control.DTO.reportDTO.SalesSummaryProjection;
import com.ffcontrol.fast_food_control.DTO.reportDTO.TopProduct;
import com.ffcontrol.fast_food_control.repository.SaleProductRepository;
import com.ffcontrol.fast_food_control.repository.SaleRepository;

@Service
public class ReportsService {
    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private SaleProductRepository saleProductRepository;

    public List<TopProduct> getTopBestSellingProducts(LocalDate startDate, LocalDate endDate, int topNumber) {
        if (endDate == null) {
            endDate = startDate;
        }

        List<Map<String, Object>> results = saleProductRepository
                                                .findTopSellingProducts(
                                                    startDate.atStartOfDay(), 
                                                    endDate.atTime(LocalTime.MAX), 
                                                    topNumber);

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

    public List<SalesSummaryProjection> getSalesSummary(LocalDate startDate, LocalDate endDate) {
        if (endDate == null) {
            endDate = startDate;
        }

        return saleRepository.findSalesSummaryByDateRange(
            startDate, 
            endDate);
    }

    public List<LessSoldProduct> getLessSellingProducts(LocalDate startDate, LocalDate endDate, int topNumber) {
        if (endDate == null) {
            endDate = startDate;
        }

        List<Map<String, Object>> results = saleProductRepository
                                                .findTopSellingProducts(
                                                    startDate.atStartOfDay(), 
                                                    endDate.atTime(LocalTime.MAX), 
                                                    topNumber);

        return results.stream()
                .map(row -> {
                    LessSoldProduct lessSoldProduct = new LessSoldProduct();
                    lessSoldProduct.setProductId(((Number) row.get("productId")).longValue());
                    lessSoldProduct.setProductName((String) row.get("productName"));
                    lessSoldProduct.setSalePrice(((Number) row.get("salePrice")).doubleValue());
                    lessSoldProduct.setTotalSold(((Number) row.get("totalSold")).intValue());
                    lessSoldProduct.setTotalSalesValue(((Number) row.get("totalSalesValue")).doubleValue());
                    return lessSoldProduct;
                })
                .toList();
    }

    public List<MonthlySales> getMonthlySales(LocalDate startDate, LocalDate endDate) {
        if (endDate == null) {
            endDate = startDate;
        }

        return saleRepository.findMonthlySales(startDate, endDate);
    }

    public List<GrowMonthlyRatio> getMonthlyGrowthRatios(LocalDate startDate, LocalDate endDate) {
        List<MonthlySales> monthlySales = saleRepository.findMonthlySales(startDate, endDate);
        List<GrowMonthlyRatio> growthRatios = new ArrayList<>();

        for (int i = 1; i < monthlySales.size(); i++) {
            Double previousMonth = monthlySales.get(i - 1).getTotalSales();
            Double currentMonth = monthlySales.get(i).getTotalSales();

            Double ratio = previousMonth == 0 ? 0 : ((currentMonth - previousMonth) / previousMonth) * 100;
            growthRatios.add(new GrowMonthlyRatio(monthlySales.get(i-1).getMonth() + " - " + monthlySales.get(i).getMonth(), ratio));
        }

        return growthRatios;
    }

    public SalesCompare compareSalesBetweenRanges(LocalDate startDate1, LocalDate endDate1,
                                                    LocalDate startDate2, LocalDate endDate2) {
        Double totalSalesRange1 = saleRepository.sumSalesInRange(startDate1, endDate1);
        Double totalSalesRange2 = saleRepository.sumSalesInRange(startDate2, endDate2);

        Double diff = totalSalesRange2 - totalSalesRange1;
        Double percentageChange = totalSalesRange1 == 0 ? 0 : (diff / totalSalesRange1) * 100;

        return new SalesCompare(totalSalesRange1, totalSalesRange2, diff, percentageChange);
    }

    public List<ProductSalesCompareResponse> compareTwoProducts(Long productId1, Long productId2) {
        List<ProductSalesCompare> comparisons = saleProductRepository.compareProductSales(
            List.of(productId1, productId2)
        );

        ProductSalesCompare product1 = comparisons.get(0);
        ProductSalesCompare product2 = comparisons.get(1);

        Long unitsDiff = product1.getTotalUnitSold() - product2.getTotalUnitSold();
        Double salesDiff = product1.getTotalSalesValue() - product2.getTotalSalesValue();

        ProductSalesCompareResponse response1 = new ProductSalesCompareResponse(
            product1.getProductName(),
            product1.getTotalUnitSold(),
            product1.getTotalSalesValue(),
            unitsDiff,
            salesDiff
        );

        ProductSalesCompareResponse response2 = new ProductSalesCompareResponse(
            product2.getProductName(),
            product2.getTotalUnitSold(),
            product2.getTotalSalesValue(),
            -unitsDiff,
            -salesDiff
        );

        List<ProductSalesCompareResponse> comparisonsResponse = new ArrayList<>();
        comparisonsResponse.add(response1);
        comparisonsResponse.add(response2);

        return comparisonsResponse;
    }
}
