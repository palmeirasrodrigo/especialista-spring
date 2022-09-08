package com.rodrigo.foodapi.api.assembler.product;

import com.rodrigo.foodapi.api.model.request.ProductRequest;
import com.rodrigo.foodapi.domain.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductRequestDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Product toDomainObject(ProductRequest productRequest) {
        return modelMapper.map(productRequest, Product.class);
    }

    public void copyToDomainObject(ProductRequest productRequest, Product product) {
        modelMapper.map(productRequest, product);
    }
}
