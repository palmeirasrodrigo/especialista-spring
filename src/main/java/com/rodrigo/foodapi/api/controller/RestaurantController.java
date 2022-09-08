package com.rodrigo.foodapi.api.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.rodrigo.foodapi.api.assembler.restaurant.RestaurantModelAssembler;
import com.rodrigo.foodapi.api.assembler.restaurant.RestaurantRequestDisassembler;
import com.rodrigo.foodapi.api.model.request.RestaurantRequest;
import com.rodrigo.foodapi.api.model.response.RestaurantResponse;
import com.rodrigo.foodapi.api.model.view.RestaurantView;
import com.rodrigo.foodapi.api.openapi.controller.RestaurantControllerOpenApi;
import com.rodrigo.foodapi.domain.exception.BusinessException;
import com.rodrigo.foodapi.domain.exception.RestaurantNotFoundException;
import com.rodrigo.foodapi.domain.model.Restaurant;
import com.rodrigo.foodapi.domain.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController implements RestaurantControllerOpenApi {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantModelAssembler restaurantModelAssembler;

    @Autowired
    private RestaurantRequestDisassembler restaurantRequestDisassembler;

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
    public List<RestaurantResponse> listAll() {
        return restaurantModelAssembler.toCollectionModel(restaurantService.listAll());
    }

    @Override
    @JsonView(RestaurantView.Resume.class)
    @GetMapping(params = "project=resume")
    public List<RestaurantResponse> listResume() {
        return listAll();
    }

    @Override
    @JsonView(RestaurantView.JustName.class)
    @GetMapping(params = "project=name")
    public List<RestaurantResponse> listNames() {
        return listAll();
    }

    @Override
    @GetMapping("/restaurant/{restaurantId}")
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
    @PutMapping("/restaurant/{restaurantId}")
    public RestaurantResponse update(@PathVariable Long restaurantId, @RequestBody @Valid RestaurantRequest restaurantRequest) {
        Restaurant actualRestaurant = restaurantService.find(restaurantId);
        restaurantRequestDisassembler.copyToDomainObject(restaurantRequest, actualRestaurant);
        return restaurantModelAssembler.toModel(restaurantService.create(actualRestaurant));
    }

    @Override
    @PatchMapping("/restaurant/{restaurantId}")
    public RestaurantResponse partialUpdate(@PathVariable Long restaurantId,
                                            @RequestBody Map<String, Object> fields, HttpServletRequest request) {
        return restaurantModelAssembler.toModel(restaurantService.partialUpdate(restaurantId, fields, request));
    }

    @Override
    @DeleteMapping("/restaurant/{restaurantId}")
    public void remove(@PathVariable Long restaurantId) {
        restaurantService.remove(restaurantId);
    }

    @Override
    @PutMapping("/restaurant/activations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activateMultiples(@RequestBody List<Long> restaurantId) {
        try {
            restaurantService.activate(restaurantId);
        }catch (RestaurantNotFoundException ex){
            throw new BusinessException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    @DeleteMapping("/restaurant/deactivations")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivationsMultiples(@RequestBody List<Long> restaurantId) {
        try {
            restaurantService.inactivate(restaurantId);
        }catch (RestaurantNotFoundException ex){
            throw new BusinessException(ex.getMessage(), ex.getCause());
        }
    }

    @Override
    @PutMapping("/restaurant/{restaurantId}/active")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void active(@PathVariable Long restaurantId) {
        restaurantService.activate(restaurantId);
    }

    @Override
    @DeleteMapping("/restaurant/{restaurantId}/inactive")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inactive(@PathVariable Long restaurantId) {
        restaurantService.inactivate(restaurantId);
    }

    @Override
    @PutMapping("/{restaurantId}/open")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void open(@PathVariable Long restaurantId) {
        restaurantService.open(restaurantId);
    }

    @Override
    @PutMapping("/{restaurantId}/close")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void close(@PathVariable Long restaurantId) {
        restaurantService.close(restaurantId);
    }
}
