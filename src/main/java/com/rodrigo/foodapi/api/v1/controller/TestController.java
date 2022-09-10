package com.rodrigo.foodapi.api.v1.controller;

import com.rodrigo.foodapi.domain.model.Kitchen;
import com.rodrigo.foodapi.domain.model.Restaurant;
import com.rodrigo.foodapi.domain.repository.KitchenRepository;
import com.rodrigo.foodapi.domain.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/test")
public class TestController {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private KitchenRepository kitchenRepository;

    @GetMapping("/restaurants/free")
    public List<Restaurant> restaurantFreeShipping(String name) {
        return restaurantRepository.restaurantFreeShipping(name);
    }

    @GetMapping("/restaurants/first")
    public Optional<Restaurant> restaurantFirst() {
        return restaurantRepository.findFirst();
    }

    @GetMapping("/kitchens/first")
    public Optional<Kitchen> kitchenFirst() {
        return kitchenRepository.findFirst();
    }
}
