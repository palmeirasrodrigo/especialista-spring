package com.rodrigo.foodapi.api.controller;

import com.rodrigo.foodapi.api.assembler.formpayment.FormPaymentModelAssembler;
import com.rodrigo.foodapi.api.assembler.user.UserModelAssembler;
import com.rodrigo.foodapi.api.model.response.FormPaymentResponse;
import com.rodrigo.foodapi.api.model.response.UserResponse;
import com.rodrigo.foodapi.api.openapi.controller.RestaurantResponsibleUserControllerOpenApi;
import com.rodrigo.foodapi.domain.model.Restaurant;
import com.rodrigo.foodapi.domain.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants/{restaurantId}/responsible")
public class RestaurantResponsibleUserController implements RestaurantResponsibleUserControllerOpenApi {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private UserModelAssembler userModelAssembler;

    @Override
    @GetMapping()
    public List<UserResponse> listAll(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantService.find(restaurantId);
        return userModelAssembler.toCollectionModel(restaurant.getResponsible());
    }

    @Override
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disassociate(@PathVariable Long restaurantId, @PathVariable Long userId){
        restaurantService.disassociateResponsible(restaurantId,userId);
    }

    @Override
    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associate(@PathVariable Long restaurantId, @PathVariable Long userId){
        restaurantService.associateResponsible(restaurantId,userId);
    }

}
