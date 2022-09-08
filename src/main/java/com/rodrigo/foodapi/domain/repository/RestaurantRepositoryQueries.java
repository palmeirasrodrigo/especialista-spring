package com.rodrigo.foodapi.domain.repository;

import com.rodrigo.foodapi.domain.model.Restaurant;

import java.math.BigDecimal;
import java.util.List;

public interface RestaurantRepositoryQueries {
    List<Restaurant> find(String name, BigDecimal startingFee, BigDecimal finalFee);
    List<Restaurant> findByCriteria(String name, BigDecimal startingFee, BigDecimal finalFee);

    List<Restaurant> restaurantFreeShipping(String name);
}
