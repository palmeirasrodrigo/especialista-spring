package com.rodrigo.foodapi.api.controller;

import com.rodrigo.foodapi.api.assembler.city.CityModelAssembler;
import com.rodrigo.foodapi.api.assembler.city.CityRequestDisassembler;
import com.rodrigo.foodapi.api.model.request.CityRequest;
import com.rodrigo.foodapi.api.model.response.CityResponse;
import com.rodrigo.foodapi.api.openapi.controller.CityControllerApi;
import com.rodrigo.foodapi.domain.model.City;
import com.rodrigo.foodapi.domain.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController implements CityControllerApi {

    @Autowired
    private CityService cityService;

    @Autowired
    private CityModelAssembler cityModelAssembler;

    @Autowired
    CityRequestDisassembler cityRequestDisassembler;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CityResponse> listAll() {
        return cityModelAssembler.toCollectionModel(cityService.listAll());
    }

    @GetMapping(value = "/city/{cityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CityResponse find(@PathVariable Long cityId) {
        return cityModelAssembler.toModel(cityService.find(cityId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    public CityResponse create(@RequestBody @Valid CityRequest cityRequest) {
        City city = cityRequestDisassembler.toDomainObject(cityRequest);
        return cityModelAssembler.toModel(cityService.create(city));
    }

    @PutMapping("/city/{cityId}")
    public CityResponse update(@PathVariable Long cityId,
                               @RequestBody @Valid CityRequest cityRequest) {
        City cityActual = cityService.find(cityId);
        cityRequestDisassembler.copyToDomainObject(cityRequest, cityActual);
        return cityModelAssembler.toModel(cityService.create(cityActual));
    }

    @DeleteMapping("/city/{cityId}")
    public void remove(@PathVariable Long cityId) {
        cityService.remove(cityId);
    }

}
