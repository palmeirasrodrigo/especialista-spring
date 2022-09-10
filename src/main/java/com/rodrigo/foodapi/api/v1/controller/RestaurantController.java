package com.rodrigo.foodapi.api.v1.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.rodrigo.foodapi.api.v1.assembler.restaurant.RestaurantBasicModelAssembler;
import com.rodrigo.foodapi.api.v1.assembler.restaurant.RestaurantModelAssembler;
import com.rodrigo.foodapi.api.v1.assembler.restaurant.RestaurantRequestDisassembler;
import com.rodrigo.foodapi.api.v1.assembler.restaurant.RestaurantResumeAssembler;
import com.rodrigo.foodapi.api.v1.model.request.RestaurantRequest;
import com.rodrigo.foodapi.api.v1.model.response.RestaurantBasicModel;
import com.rodrigo.foodapi.api.v1.model.response.RestaurantResponse;
import com.rodrigo.foodapi.api.v1.model.response.RestaurantResumeResponse;
import com.rodrigo.foodapi.api.v1.model.view.RestaurantView;
import com.rodrigo.foodapi.api.v1.openapi.controller.RestaurantControllerOpenApi;
import com.rodrigo.foodapi.domain.exception.BusinessException;
import com.rodrigo.foodapi.domain.exception.RestaurantNotFoundException;
import com.rodrigo.foodapi.domain.model.Restaurant;
import com.rodrigo.foodapi.domain.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/v1/restaurants", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController implements RestaurantControllerOpenApi {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantModelAssembler restaurantModelAssembler;

    @Autowired
    private RestaurantRequestDisassembler restaurantRequestDisassembler;

    @Autowired
    private RestaurantBasicModelAssembler restaurantBasicModelAssembler;

    @Autowired
    private RestaurantResumeAssembler restaurantResumeAssembler;

//    @GetMapping()
//    public MappingJacksonValue listAll(@RequestParam(required = false) String project) {
//        List<Restaurant> restaurants = restaurantService.listAll();
//        List<RestaurantResponse> restaurantResponses = restaurantModelAssembler.toCollectionModel(restaurants);
//
//        MappingJacksonValue restaurantWrapper = new MappingJacksonValue(restaurantResponses);
//
//        restaurantWrapper.setSerializationView(RestaurantView.Resume.class);
//
//        if("name".equals(project)){
//            restaurantWrapper.setSerializationView(RestaurantView.JustName.class);
//        } else if ("complete".equals(project)) {
//            restaurantWrapper.setSerializationView(null);
//        }
//
//        return restaurantWrapper;
//    }

    @Override
    @GetMapping()
    public CollectionModel<RestaurantBasicModel> listAll() {
        return restaurantBasicModelAssembler.toCollectionModel(restaurantService.listAll());
    }

    @Override
    @JsonView(RestaurantView.Resume.class)
    @GetMapping(params = "project=resume")
    public CollectionModel<RestaurantResponse> listResume() {
        return restaurantModelAssembler.toCollectionModel(restaurantService.listAll());
    }

    @Override
    @JsonView(RestaurantView.JustName.class)
    @GetMapping(params = "project=name")
    public CollectionModel<RestaurantResumeResponse> listNames() {
        return restaurantResumeAssembler.toCollectionModel(restaurantService.listAll());
    }

    @Override
    @GetMapping("/{restaurantId}")
    public RestaurantResponse find(@PathVariable Long restaurantId) {
        return restaurantModelAssembler.toModel(restaurantService.find(restaurantId));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantResponse create(@RequestBody @Valid RestaurantRequest restaurantRequest) {
        Restaurant restaurant = restaurantRequestDisassembler.toDomainObject(restaurantRequest);

        return restaurantModelAssembler.toModel(restaurantService.create(restaurant));
    }

    @Override
    @PutMapping("/{restaurantId}")
    public RestaurantResponse update(@PathVariable Long restaurantId, @RequestBody @Valid RestaurantRequest restaurantRequest) {
        Restaurant actualRestaurant = restaurantService.find(restaurantId);
        restaurantRequestDisassembler.copyToDomainObject(restaurantRequest, actualRestaurant);
        return restaurantModelAssembler.toModel(restaurantService.create(actualRestaurant));
    }

    @Override
    @PatchMapping("/{restaurantId}")
    public RestaurantResponse partialUpdate(@PathVariable Long restaurantId,
                                            @RequestBody Map<String, Object> fields, HttpServletRequest request) {
        return restaurantModelAssembler.toModel(restaurantService.partialUpdate(restaurantId, fields, request));
    }

    @Override
    @DeleteMapping("/{restaurantId}")
    public void remove(@PathVariable Long restaurantId) {
        restaurantService.remove(restaurantId);
    }

    @Override
    @PutMapping("/activations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activateMultiples(@RequestBody List<Long> restaurantId) {
        try {
            restaurantService.activate(restaurantId);
        } catch (RestaurantNotFoundException ex) {
            throw new BusinessException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    @DeleteMapping("/deactivations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivationsMultiples(@RequestBody List<Long> restaurantId) {
        try {
            restaurantService.inactivate(restaurantId);
        } catch (RestaurantNotFoundException ex) {
            throw new BusinessException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    @PutMapping("/{restaurantId}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> active(@PathVariable Long restaurantId) {
        restaurantService.activate(restaurantId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @DeleteMapping("/{restaurantId}/inactive")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> inactive(@PathVariable Long restaurantId) {
        restaurantService.inactivate(restaurantId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{restaurantId}/open")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> open(@PathVariable Long restaurantId) {
        restaurantService.open(restaurantId);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PutMapping("/{restaurantId}/close")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> close(@PathVariable Long restaurantId) {
        restaurantService.close(restaurantId);
        return ResponseEntity.noContent().build();
    }
}
