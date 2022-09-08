package com.rodrigo.foodapi.infrastructure.repository.spec;

import com.rodrigo.foodapi.domain.model.Restaurant;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class RestaurantSpecs {

    public static Specification<Restaurant> freeShipping(){
        return ((root, query, builder) -> builder.equal(root.get("shippingFee"), BigDecimal.ZERO));
    }

    public static Specification<Restaurant> similarName(String name){
        return ((root, query, builder) -> builder.like(root.get("name"), "%" + name + "%"));
    }
}
