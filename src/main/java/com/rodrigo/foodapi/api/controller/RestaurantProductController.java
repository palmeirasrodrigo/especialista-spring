package com.rodrigo.foodapi.api.controller;

import com.rodrigo.foodapi.api.assembler.product.ProductModelAssembler;
import com.rodrigo.foodapi.api.assembler.product.ProductRequestDisassembler;
import com.rodrigo.foodapi.api.model.request.ProductRequest;
import com.rodrigo.foodapi.api.model.response.ProductResponse;
import com.rodrigo.foodapi.api.openapi.controller.RestaurantProductControllerOpenApi;
import com.rodrigo.foodapi.domain.model.Product;
import com.rodrigo.foodapi.domain.model.Restaurant;
import com.rodrigo.foodapi.domain.repository.ProductRepository;
import com.rodrigo.foodapi.domain.service.ProductService;
import com.rodrigo.foodapi.domain.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/restaurants/{restaurantId}/products")
public class RestaurantProductController implements RestaurantProductControllerOpenApi {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private ProductModelAssembler productModelAssembler;

    @Autowired
    private ProductRequestDisassembler productRequestDisassembler;

    @Override
    @GetMapping()
    public List<ProductResponse> listAll(@PathVariable Long restaurantId, @RequestParam(required = false) boolean addInactive) {
        Restaurant restaurant = restaurantService.find(restaurantId);
        List<Product> allProducts = null;

        if (addInactive) {
            allProducts = productRepository.findByRestaurant(restaurant);
        } else {
            allProducts = productRepository.findActiveByRestaurant(restaurant);
        }
        return productModelAssembler.toCollectionModel(allProducts);
    }

    @Override
    @GetMapping("/{productId}")
    public ProductResponse find(@PathVariable Long restaurantId, @PathVariable Long productId) {
        return productModelAssembler.toModel(productService.find(restaurantId, productId));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse add(@PathVariable Long restaurantId,
                               @RequestBody @Valid ProductRequest productRequest) {
        Restaurant restaurant = restaurantService.find(restaurantId);

        Product product = productRequestDisassembler.toDomainObject(productRequest);
        product.setRestaurant(restaurant);

        product = productService.create(product);

        return productModelAssembler.toModel(product);
    }

    @Override
    @PutMapping("/{productId}")
    public ProductResponse update(@PathVariable Long restaurantId, @PathVariable Long productId,
                                  @RequestBody @Valid ProductRequest productRequest) {
        Product actualProduct = productService.find(restaurantId, productId);

        productRequestDisassembler.copyToDomainObject(productRequest, actualProduct);

        actualProduct = productService.create(actualProduct);

        return productModelAssembler.toModel(actualProduct);
    }
}
