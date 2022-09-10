package com.rodrigo.foodapi.api.v1.controller;

import com.rodrigo.foodapi.api.ResourceUriHelper;
import com.rodrigo.foodapi.api.v1.assembler.city.CityModelAssembler;
import com.rodrigo.foodapi.api.v1.assembler.city.CityRequestDisassembler;
import com.rodrigo.foodapi.api.v1.model.request.CityRequest;
import com.rodrigo.foodapi.api.v1.model.response.CityResponse;
import com.rodrigo.foodapi.api.v1.openapi.controller.CityControllerApi;
import com.rodrigo.foodapi.domain.model.City;
import com.rodrigo.foodapi.domain.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/v1/cities", produces = MediaType.APPLICATION_JSON_VALUE)
public class CityController implements CityControllerApi {

    @Autowired
    private CityService cityService;

    @Autowired
    private CityModelAssembler cityModelAssembler;

    @Autowired
    CityRequestDisassembler cityRequestDisassembler;

    @Deprecated
    @Override
    @GetMapping
    public CollectionModel<CityResponse> listAll() {
        List<City> cities = cityService.listAll();
        return cityModelAssembler.toCollectionModel(cities);
    }

    @GetMapping(value = "/{cityId}")
    public CityResponse find(@PathVariable Long cityId) {
        return cityModelAssembler.toModel(cityService.find(cityId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CityResponse create(@RequestBody @Valid CityRequest cityRequest) {
        City city = cityRequestDisassembler.toDomainObject(cityRequest);
        city = cityService.create(city);

        CityResponse cityResponse = cityModelAssembler.toModel(city);

        ResourceUriHelper.addUriInResponseHeader(cityResponse.getId());

        return cityResponse;
    }

    @PutMapping("/{cityId}")
    public CityResponse update(@PathVariable Long cityId,
                               @RequestBody @Valid CityRequest cityRequest) {
        City cityActual = cityService.find(cityId);
        cityRequestDisassembler.copyToDomainObject(cityRequest, cityActual);
        return cityModelAssembler.toModel(cityService.create(cityActual));
    }

    @DeleteMapping("/{cityId}")
    public void remove(@PathVariable Long cityId) {
        cityService.remove(cityId);
    }

}
