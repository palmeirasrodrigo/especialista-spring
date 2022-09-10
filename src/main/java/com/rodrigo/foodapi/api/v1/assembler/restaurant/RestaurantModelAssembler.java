package com.rodrigo.foodapi.api.v1.assembler.restaurant;

import com.rodrigo.foodapi.api.v1.AlgaLinks;
import com.rodrigo.foodapi.api.v1.controller.RestaurantController;
import com.rodrigo.foodapi.api.v1.model.response.RestaurantResponse;
import com.rodrigo.foodapi.domain.model.Restaurant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class RestaurantModelAssembler extends RepresentationModelAssemblerSupport<Restaurant, RestaurantResponse> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public RestaurantModelAssembler() {
        super(RestaurantController.class, RestaurantResponse.class);
    }


    @Override
    public RestaurantResponse toModel(Restaurant restaurant) {
        RestaurantResponse restaurantModel = createModelWithId(restaurant.getId(), restaurant);
        modelMapper.map(restaurant, restaurantModel);

        restaurantModel.add(algaLinks.linkToRestaurants("restaurants"));

        if (restaurant.activationAllowed()) {
            restaurantModel.add(
                    algaLinks.linkToRestaurantActive(restaurant.getId(), "active"));
        }

        if (restaurant.inactivationAllowed()) {
            restaurantModel.add(
                    algaLinks.linkToRestaurantInactivation(restaurant.getId(), "inactivate"));
        }

        if (restaurant.openingAllowed()) {
            restaurantModel.add(
                    algaLinks.linkToRestaurantOpen(restaurant.getId(), "open"));
        }

        if (restaurant.closingAllowed()) {
            restaurantModel.add(
                    algaLinks.linkToRestaurantClose(restaurant.getId(), "close"));
        }

        restaurantModel.add(algaLinks.linkToProducts(restaurant.getId(), "products"));

        restaurantModel.getKitchen().add(
                algaLinks.linkToKitchen(restaurant.getKitchen().getId()));

        if (restaurantModel.getAddress() != null
                && restaurantModel.getAddress().getCity() != null) {
            restaurantModel.getAddress().getCity().add(
                    algaLinks.linkToCity(restaurant.getAddress().getCity().getId()));
        }

        restaurantModel.add(algaLinks.linkToRestaurantFormPayment(restaurant.getId(),
                "form-payment"));

        restaurantModel.add(algaLinks.linkToResponsibleRestaurant(restaurant.getId(),
                "responsible"));

        return restaurantModel;
    }

    @Override
    public CollectionModel<RestaurantResponse> toCollectionModel(Iterable<? extends Restaurant> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToRestaurants());
    }

//    public RestaurantResponse toModel(Restaurant restaurant) {
//        return modelMapper.map(restaurant, RestaurantResponse.class);
//    }
//
//    public List<RestaurantResponse> toCollectionModel(List<Restaurant> restaurants) {
//        return restaurants.stream()
//                .map(this::toModel)
//                .toList();
//    }
}
