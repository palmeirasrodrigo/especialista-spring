package com.rodrigo.foodapi.api.v1.controller;

import com.rodrigo.foodapi.api.v1.AlgaLinks;
import com.rodrigo.foodapi.api.v1.assembler.formpayment.FormPaymentModelAssembler;
import com.rodrigo.foodapi.api.v1.model.response.FormPaymentResponse;
import com.rodrigo.foodapi.api.v1.openapi.controller.RestaurantFormPaymentControllerOpenApi;
import com.rodrigo.foodapi.domain.model.Restaurant;
import com.rodrigo.foodapi.domain.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/restaurants/{restaurantId}/form-payment", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantFormPaymentController implements RestaurantFormPaymentControllerOpenApi {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private FormPaymentModelAssembler formPaymentModelAssembler;

    @Autowired
    private AlgaLinks algaLinks;

    @Override
    @GetMapping
    public CollectionModel<FormPaymentResponse> listAll(@PathVariable Long restaurantId) {
        Restaurant restaurant = restaurantService.find(restaurantId);

        CollectionModel<FormPaymentResponse> formPaymentResponse
                = formPaymentModelAssembler.toCollectionModel(restaurant.getFormPayments())
                .removeLinks()
                .add(algaLinks.linkToRestaurantFormPayment(restaurantId))
                .add(algaLinks.linkToRestaurantLinkToRestaurantFormPaymentAssociation(restaurantId, "associate"));

        formPaymentResponse.getContent().forEach(formPayment -> formPayment.add(algaLinks.linkToRestaurantLinkToRestaurantFormPaymentDisassociation(
                restaurantId, formPayment.getId(), "disassociate")));

        return formPaymentResponse;
    }

    @Override
    @DeleteMapping("/{formPaymentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> disassociate(@PathVariable Long restaurantId, @PathVariable Long formPaymentId){
        restaurantService.disassociateFormPayment(restaurantId,formPaymentId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{formPaymentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> associate(@PathVariable Long restaurantId, @PathVariable Long formPaymentId){
        restaurantService.associateFormPayment(restaurantId,formPaymentId);
        return ResponseEntity.noContent().build();
    }

}
