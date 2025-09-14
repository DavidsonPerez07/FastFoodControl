package com.ffcontrol.fast_food_control.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ffcontrol.fast_food_control.DTO.ProductDTO;
import com.ffcontrol.fast_food_control.DTO.ProductQuantity;
import com.ffcontrol.fast_food_control.DTO.SaleDTO;
import com.ffcontrol.fast_food_control.entity.Sale;
import com.ffcontrol.fast_food_control.entity.SaleProduct;

@Configuration
public class MapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        Converter<SaleProduct, ProductQuantity> saleProductToProductQuantity = context -> {
            SaleProduct saleProduct = context.getSource();

            if (saleProduct == null) return null;

            ProductQuantity productQuantity = new ProductQuantity();
            productQuantity.setProduct(mapper.map(saleProduct.getProduct(), ProductDTO.class));
            productQuantity.setQuantity(saleProduct.getQuantity());
            return productQuantity;
        };

        mapper.addConverter(saleProductToProductQuantity, SaleProduct.class, ProductQuantity.class);

        mapper.typeMap(Sale.class, SaleDTO.class)
            .addMappings(m -> m.map(Sale::getSaleProducts, SaleDTO::setItems));

        return mapper;
    }
}
