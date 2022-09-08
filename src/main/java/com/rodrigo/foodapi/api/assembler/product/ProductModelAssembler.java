package com.rodrigo.foodapi.api.assembler.product;

import com.rodrigo.foodapi.api.model.response.ProductResponse;
import com.rodrigo.foodapi.domain.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public ProductResponse toModel(Product product) {
        return modelMapper.map(product, ProductResponse.class);
    }

    public List<ProductResponse> toCollectionModel(List<Product> products) {
        return products.stream()
                .map(this::toModel)
                .toList();
    }
}
