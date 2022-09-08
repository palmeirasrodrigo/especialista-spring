package com.rodrigo.foodapi.api.controller;

import com.rodrigo.foodapi.api.assembler.formpayment.FormPaymentModelAssembler;
import com.rodrigo.foodapi.api.model.response.FormPaymentResponse;
import com.rodrigo.foodapi.api.openapi.controller.RestaurantFormPaymentControllerOpenApi;
import com.rodrigo.foodapi.domain.model.Restaurant;
import com.rodrigo.foodapi.domain.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants/{restaurantId}/form-payment")
public class RestaurantFormPaymentController implements RestaurantFormPaymentControllerOpenApi {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private FormPaymentModelAssembler formPaymentModelAssembler;

    @Override
    @GetMapping()
    public List<FormPaymentResponse> listAll(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantService.find(restaurantId);
        return formPaymentModelAssembler.toCollectionModel(restaurant.getFormPayments());
    }

    @Override
    @DeleteMapping("/{formPaymentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disassociate(@PathVariable Long restaurantId, @PathVariable Long formPaymentId){
        restaurantService.disassociateFormPayment(restaurantId,formPaymentId);
    }

    @Override
    @PutMapping("/{formPaymentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associate(@PathVariable Long restaurantId, @PathVariable Long formPaymentId){
        restaurantService.associateFormPayment(restaurantId,formPaymentId);
    }

}
