package com.ffcontrol.fast_food_control.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ffcontrol.fast_food_control.DTO.reportDTO.ProductSalesCompare;
import com.ffcontrol.fast_food_control.DTO.reportDTO.SalesSummary;
import com.ffcontrol.fast_food_control.entity.SaleProduct;

@Repository
public interface SaleProductRepository extends JpaRepository<SaleProduct, Long> {
    @Query(value = """
            SELECT 
                p.product_id AS productId,
                p.product_name AS productName,
                p.sale_price AS salePrice,
                SUM(sp.quantity) AS totalSold,
                SUM(sp.quantity * p.sale_price) AS totalSalesValue
            FROM sale_products sp
            JOIN products p ON sp.product_id = p.product_id
            JOIN sales s ON sp.sale_id = s.sale_id
            WHERE s.sale_date BETWEEN :startDate AND :endDate
            GROUP BY p.product_id, p.product_name, p.sale_price
            ORDER BY totalSold DESC
            LIMIT :limit
            """, nativeQuery = true)
    List<Map<String, Object>> findTopSellingProducts(
        @Param("startDate") LocalDateTime startDate, 
        @Param("endDate") LocalDateTime endDate, 
        @Param("limit") int limit
    );

    @Query(value = """
            SELECT 
                p.product_id AS productId,
                p.product_name AS productName,
                p.sale_price AS salePrice,
                SUM(sp.quantity) AS totalSold,
                SUM(sp.quantity * p.sale_price) AS totalSalesValue
            FROM sale_products sp
            JOIN products p ON sp.product_id = p.product_id
            JOIN sales s ON sp.sale_id = s.sale_id
            WHERE s.sale_date BETWEEN :startDate AND :endDate
            GROUP BY p.product_id, p.product_name, p.sale_price
            ORDER BY totalSold ASC
            LIMIT :limit
            """, nativeQuery = true)
    List<Map<String, Object>> findLessSellingProducts(
        @Param("startDate") LocalDateTime startDate, 
        @Param("endDate") LocalDateTime endDate, 
        @Param("limit") int limit
    );

    @Query(value = """
            SELECT 
                p.product_name AS productId,
                COALESCE(SUM(sp.quantity, 0) AS totalUnitSold
                COALESCE(SUM(sp.quantity * p.sale_price), 0) AS totalSalesValue
            FROM sale_products sp
            INNER JOIN products p ON sp.product_id = p.product_id
            INNER JOIN sales s ON sp.sale_id = s.sale_id
            WHERE sp.product_id IN :productIds
            GROUP BY p.product_name
            ORDER BY totalUnitSold DESC
            """, nativeQuery = true)
    List<ProductSalesCompare> compareProductSales(
        @Param("productIds") List<Long> productIds
    );
}
