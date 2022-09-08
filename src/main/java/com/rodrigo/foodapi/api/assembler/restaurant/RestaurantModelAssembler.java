package com.rodrigo.foodapi.api.assembler.restaurant;

import com.rodrigo.foodapi.api.model.response.KitchenResponse;
import com.rodrigo.foodapi.api.model.response.RestaurantResponse;
import com.rodrigo.foodapi.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RestaurantModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public RestaurantResponse toModel(Restaurant restaurant) {
        return modelMapper.map(restaurant, RestaurantResponse.class);
    }

    public List<RestaurantResponse> toCollectionModel(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(this::toModel)
                .toList();
    }
}
