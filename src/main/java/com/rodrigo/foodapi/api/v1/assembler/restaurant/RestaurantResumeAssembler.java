package com.rodrigo.foodapi.api.v1.assembler.restaurant;

import com.rodrigo.foodapi.api.v1.AlgaLinks;
import com.rodrigo.foodapi.api.v1.controller.RestaurantController;
import com.rodrigo.foodapi.api.v1.model.response.RestaurantResumeResponse;
import com.rodrigo.foodapi.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestaurantResumeAssembler extends RepresentationModelAssemblerSupport<Restaurant, RestaurantResumeResponse> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public RestaurantResumeAssembler() {
        super(RestaurantController.class, RestaurantResumeResponse.class);
    }

    @Override
    public RestaurantResumeResponse toModel(Restaurant restaurant) {
        RestaurantResumeResponse restaurantModel = createModelWithId(
                restaurant.getId(), restaurant);

        modelMapper.map(restaurant, restaurantModel);

        restaurantModel.add(algaLinks.linkToRestaurants("restaurants"));

        return restaurantModel;
    }

    @Override
    public CollectionModel<RestaurantResumeResponse> toCollectionModel(Iterable<? extends Restaurant> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToRestaurants());
    }

}
