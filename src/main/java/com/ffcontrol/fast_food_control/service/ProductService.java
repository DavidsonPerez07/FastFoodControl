package com.ffcontrol.fast_food_control.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ffcontrol.fast_food_control.DTO.DirectCost;
import com.ffcontrol.fast_food_control.DTO.Ingredient;
import com.ffcontrol.fast_food_control.DTO.ProductDTO;
import com.ffcontrol.fast_food_control.DTO.ProductEdit;
import com.ffcontrol.fast_food_control.DTO.ProductRequest;
import com.ffcontrol.fast_food_control.DTO.ProductSaleTable;
import com.ffcontrol.fast_food_control.entity.Product;
import com.ffcontrol.fast_food_control.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CloudinaryService cloudinaryService;

    public ProductDTO createProduct(ProductRequest productRequest) throws IOException {
        Product product = new Product();

        product.setProductName(productRequest.getProductName());
        product.setIngredients(productRequest.getIngredients()
                                .stream()
                                .map(Ingredient::getName)
                                .toList());
        
        var totalIngredientsCosts = productRequest.getIngredients()
                                        .stream()
                                        .mapToDouble(Ingredient::getCost)
                                        .sum();

        var totalDirectCosts = productRequest.getDirectCosts()
                                .stream()
                                .mapToDouble(DirectCost::getCost)
                                .sum();

        product.setNetPrice(totalIngredientsCosts + totalDirectCosts + productRequest.getLabour());
        product.setProfitMargin(productRequest.getProfitMargin());

        var salePrice = product.getNetPrice() + (product.getNetPrice() * product.getProfitMargin() / 100);
        product.setSalePrice(salePrice);

        try {
            if (productRequest.getImage() != null && !productRequest.getImage().isEmpty()) {
                Map uploadResult = cloudinaryService.uploadFile(productRequest.getImage());
                product.setImageUrl(uploadResult.get("secure_url").toString());
            }
            else {
                throw new RuntimeException("Image file is missing");
            }
        } catch (Exception e) {
            throw new IOException("Error storing product image" + e.getMessage());
        }

        product.setIsActive(true);

        product = productRepository.save(product);

        return convertToDTO(product);
    }

    public ProductDTO editProduct(Long productId, ProductEdit editRequest) {
        Product product = productRepository.findById(productId)
                                .orElseThrow(() -> new RuntimeException("Product not found"));


        if (editRequest.getProductName() != null && !editRequest.getProductName().isEmpty()) {
            product.setProductName(editRequest.getProductName());
        }

        if (editRequest.getNetPrice() != null && editRequest.getNetPrice() >= 0) {
            product.setNetPrice(editRequest.getNetPrice());
        }
        
        if (editRequest.getProfitMargin() != null) {
            product.setProfitMargin(editRequest.getProfitMargin());
        }
        
        product.setSalePrice(product.getNetPrice() + (product.getNetPrice() * product.getProfitMargin() / 100));

        product = productRepository.save(product);

        return convertToDTO(product);
    }

    public ProductDTO getProductById(Long productId) {
        return convertToDTO(productRepository.findById(productId)
                            .orElseThrow(() -> new RuntimeException("Product not found")));
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .filter(product -> product.getIsActive())
                .map(this::convertToDTO)
                .toList();
    }

    public List<ProductDTO> getInactiveProducts() {
        return productRepository.findAll()
                .stream()
                .filter(product -> !product.getIsActive())
                .map(this::convertToDTO)
                .toList();
    }

    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getImageUrl() != null) {
            try {
                String imageUrl = product.getImageUrl();
                String publicId = imageUrl.substring(imageUrl.lastIndexOf("/") + 1, imageUrl.lastIndexOf("."));
                cloudinaryService.deleteFile(publicId);
            } catch (IOException e) {
                throw new RuntimeException("Error deleting product image: " + e.getMessage());
            }
        }
        
        product.setIsActive(false);
        productRepository.save(product);
    }

    public List<ProductSaleTable> getProductsForSaleTable() {
        return productRepository.findAll()
                .stream()
                .filter(product -> product.getIsActive())
                .map(product -> new ProductSaleTable(product.getProductId(), product.getProductName(), product.getSalePrice()))
                .toList();
    }

    private ProductDTO convertToDTO(Product product) {
        return modelMapper.map(product, ProductDTO.class);
    }
}
