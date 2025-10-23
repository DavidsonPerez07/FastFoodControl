package com.ffcontrol.fast_food_control.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffcontrol.fast_food_control.DTO.ProductQuantity;
import com.ffcontrol.fast_food_control.DTO.SaleDTO;
import com.ffcontrol.fast_food_control.DTO.SaleRequest;
import com.ffcontrol.fast_food_control.entity.Product;
import com.ffcontrol.fast_food_control.entity.Sale;
import com.ffcontrol.fast_food_control.entity.SaleProduct;
import com.ffcontrol.fast_food_control.repository.ProductRepository;
import com.ffcontrol.fast_food_control.repository.SaleRepository;

@Service
public class SalesService {
    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    public SaleDTO registerSale(SaleRequest request) {
        Sale sale = new Sale();

        sale.setSaleDate(request.getSaleDate());
        sale.setConcept(request.getConcept());
        sale.setPaymentMethod(request.getPaymentMethod());

        double totalPrice = 0.0;
        List<SaleProduct> saleProducts = new ArrayList<>();

        for (ProductQuantity prodQuant : request.getItems()) {
            Product product = productRepository.findById(prodQuant.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            SaleProduct saleProduct = new SaleProduct();
            saleProduct.setSale(sale);
            saleProduct.setProduct(product);
            saleProduct.setQuantity(prodQuant.getQuantity());

            saleProducts.add(saleProduct);

            totalPrice += product.getSalePrice() * prodQuant.getQuantity();
        }

        sale.setSaleProducts(saleProducts);
        sale.setTotalPrice(totalPrice);

        sale = saleRepository.save(sale);

        return convertToDTO(sale);
    }

    public SaleDTO getSaleById(Long saleId) {
        Sale sale = saleRepository.findById(saleId)
                .orElseThrow(() -> new RuntimeException("Sale not found"));

        return convertToDTO(sale);
    }

    public List<SaleDTO> getAllSales() {
        return saleRepository.findAllByOrderBySaleDateDesc()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<SaleDTO> getSalesByDay(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        return saleRepository.findBySaleDateBetween(startOfDay, endOfDay)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<SaleDTO> getSalesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return saleRepository.findBySaleDateBetween(startDate, endDate)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public void deleteSale(Long saleId) {
        if (!saleRepository.existsById(saleId)) {
            throw new RuntimeException("Sale not found");
        }

        saleRepository.deleteById(saleId);
    }

    private SaleDTO convertToDTO(Sale sale) {
        return modelMapper.map(sale, SaleDTO.class);
    }
}
