package com.ffcontrol.fast_food_control.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffcontrol.fast_food_control.DTO.ProductDTO;
import com.ffcontrol.fast_food_control.DTO.ProductEdit;
import com.ffcontrol.fast_food_control.DTO.ProductRequest;
import com.ffcontrol.fast_food_control.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping(value = "/create-product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDTO> createProduct(@RequestPart String request, 
                                                    @RequestPart MultipartFile image) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ProductRequest requestObj = mapper.readValue(request, ProductRequest.class);
        requestObj.setImage(image);
        return ResponseEntity.ok(productService.createProduct(requestObj));
    }

    @PutMapping("/edit-product")
    public ResponseEntity<ProductDTO> editProduct(@RequestParam Long productId, @RequestBody ProductEdit editRequest) {
        return ResponseEntity.ok(productService.editProduct(productId, editRequest));
    }

    @GetMapping("/get-all-products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/get-product")
    public ResponseEntity<ProductDTO> getProductById(@RequestParam Long productId) {
        return ResponseEntity.ok(productService.getProductById(productId));
    }

    @DeleteMapping("/delete-product")
    public ResponseEntity<String> deleteProduct(@RequestParam Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Product deleted successfully");
    }
}
