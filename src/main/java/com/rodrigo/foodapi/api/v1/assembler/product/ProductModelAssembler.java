package com.rodrigo.foodapi.api.v1.assembler.product;

import com.rodrigo.foodapi.api.v1.AlgaLinks;
import com.rodrigo.foodapi.api.v1.controller.RestaurantProductController;
import com.rodrigo.foodapi.api.v1.model.response.ProductResponse;
import com.rodrigo.foodapi.domain.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class ProductModelAssembler extends RepresentationModelAssemblerSupport<Product, ProductResponse> {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AlgaLinks algaLinks;

    public ProductModelAssembler() {
        super(RestaurantProductController.class, ProductResponse.class);
    }

    @Override
    public ProductResponse toModel(Product product) {
        ProductResponse productResponse = createModelWithId(
                product.getId(), product, product.getRestaurant().getId());

        modelMapper.map(product, productResponse);

        productResponse.add(algaLinks.linkToProducts(product.getRestaurant().getId(), "products"));

        productResponse.add(algaLinks.linkToPhotoProduct(
                product.getRestaurant().getId(), product.getId(), "photo"));

        return productResponse;
    }

}
