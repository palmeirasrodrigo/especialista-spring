package com.rodrigo.foodapi.api.v1.controller;

import com.rodrigo.foodapi.api.v1.AlgaLinks;
import com.rodrigo.foodapi.api.v1.assembler.user.UserModelAssembler;
import com.rodrigo.foodapi.api.v1.model.response.UserResponse;
import com.rodrigo.foodapi.api.v1.openapi.controller.RestaurantResponsibleUserControllerOpenApi;
import com.rodrigo.foodapi.domain.model.Restaurant;
import com.rodrigo.foodapi.domain.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/restaurants/{restaurantId}/responsible", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantResponsibleUserController implements RestaurantResponsibleUserControllerOpenApi {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserModelAssembler userModelAssembler;

    @Autowired
    private AlgaLinks algaLinks;

    @Override
    @GetMapping
    public CollectionModel<UserResponse> listAll(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantService.find(restaurantId);

        CollectionModel<UserResponse> userResponse = userModelAssembler
                .toCollectionModel(restaurant.getResponsible())
                .removeLinks()
                .add(algaLinks.linkToResponsibleRestaurant(restaurantId))
                .add(algaLinks.linkToResponsibleRestaurantAssociation(restaurantId, "associate"));

        userResponse.getContent().stream().forEach(user -> {
            user.add(algaLinks.linkToResponsibleRestaurantDisassociation(
                    restaurantId, user.getId(), "disassociate"));
        });

        return userResponse;
    }

    @Override
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> disassociate(@PathVariable Long restaurantId, @PathVariable Long userId){
        restaurantService.disassociateResponsible(restaurantId,userId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associate(@PathVariable Long restaurantId, @PathVariable Long userId){
        restaurantService.associateResponsible(restaurantId,userId);
        return ResponseEntity.noContent().build();
    }

}
