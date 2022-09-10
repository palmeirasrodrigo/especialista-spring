package com.rodrigo.foodapi.api.v1.assembler.restaurant;

import com.rodrigo.foodapi.api.v1.model.request.RestaurantRequest;
import com.rodrigo.foodapi.domain.model.Address;
import com.rodrigo.foodapi.domain.model.Kitchen;
import com.rodrigo.foodapi.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestaurantRequestDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Restaurant toDomainObject(RestaurantRequest restaurantRequest) {
        return modelMapper.map(restaurantRequest, Restaurant.class);
    }

    public void copyToDomainObject(RestaurantRequest restaurantRequest, Restaurant restaurant){
        restaurant.setKitchen(new Kitchen()); //remover o erro de mudar id
        if(restaurant.getAddress() != null){
            restaurant.setAddress(new Address());
        }
        modelMapper.map(restaurantRequest, restaurant);
    }
}
