package com.ffcontrol.fast_food_control.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ffcontrol.fast_food_control.DTO.reportDTO.MonthlySales;
import com.ffcontrol.fast_food_control.DTO.reportDTO.SalesSummary;
import com.ffcontrol.fast_food_control.DTO.reportDTO.SalesSummaryProjection;
import com.ffcontrol.fast_food_control.entity.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long>{
    List<Sale> findAllByOrderBySaleDateDesc();

    List<Sale> findBySaleDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = """
            SELECT 
                DATE(s.sale_date) AS date,
                SUM(s.total_price) AS totalSales,
                COUNT(s.sale_id) AS totalOrders
            FROM sales s
            WHERE DATE(s.sale_date) BETWEEN :startDate AND :endDate
            GROUP BY DATE(s.sale_date)
            ORDER BY DATE(s.sale_date)
            """, nativeQuery = true)
    List<SalesSummaryProjection> findSalesSummaryByDateRange(
        @Param("startDate") LocalDate startDate, 
        @Param("endDate") LocalDate endDate
    );

    @Query(value = """
            SELECT 
                CAST(EXTRACT(YEAR FROM s.sale_date) AS INTEGER) AS year,
                CAST(EXTRACT(MONTH FROM s.sale_date) AS INTEGER) AS month,
                SUM(s.total_price) AS totalSales
            FROM sales s
            WHERE s.sale_date BETWEEN :startDate AND :endDate
            GROUP BY year, month
            ORDER BY year, month
            """, nativeQuery = true)
    List<MonthlySales> findMonthlySales(
        @Param("startDate") LocalDate startDate, 
        @Param("endDate") LocalDate endDate
    );

    @Query(value = """
            SELECT 
                COALESCE(SUM(s.total_price), 0)
            FROM sales s
            WHERE s.sale_date BETWEEN :startDate AND :endDate
            """, nativeQuery = true)
    Double sumSalesInRange(
        @Param("startDate") LocalDate startDate, 
        @Param("endDate") LocalDate endDate
    );
}
