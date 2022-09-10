package com.rodrigo.foodapi.api.v2.controller;

import com.rodrigo.foodapi.api.ResourceUriHelper;
import com.rodrigo.foodapi.api.v2.assembler.city.CityModelAssemblerV2;
import com.rodrigo.foodapi.api.v2.assembler.city.CityRequestDisassemblerV2;
import com.rodrigo.foodapi.api.v2.model.request.CityRequestV2;
import com.rodrigo.foodapi.api.v2.model.response.CityResponseV2;
import com.rodrigo.foodapi.api.v2.openapi.CityControllerV2OpenApi;
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
@RequestMapping(path = "/v2/cities", produces = MediaType.APPLICATION_JSON_VALUE)
public class CityControllerV2 implements CityControllerV2OpenApi {

    @Autowired
    private CityService cityService;

    @Autowired
    private CityModelAssemblerV2 cityModelAssemblerV2;

    @Autowired
    private CityRequestDisassemblerV2 cityRequestDisassembler;

    @Deprecated
    @GetMapping
    public CollectionModel<CityResponseV2> listAll() {
        List<City> cities = cityService.listAll();
        return cityModelAssemblerV2.toCollectionModel(cities);
    }

    @GetMapping(value = "/{cityId}")
    public CityResponseV2 find(@PathVariable Long cityId) {
        return cityModelAssemblerV2.toModel(cityService.find(cityId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public CityResponseV2 create(@RequestBody @Valid CityRequestV2 cityRequest) {
        City city = cityRequestDisassembler.toDomainObject(cityRequest);
        city = cityService.create(city);

        CityResponseV2 cityResponse = cityModelAssemblerV2.toModel(city);

        ResourceUriHelper.addUriInResponseHeader(cityResponse.getCityId());

        return cityResponse;
    }

    @PutMapping("/{cityId}")
    public CityResponseV2 update(@PathVariable Long cityId,
                                 @RequestBody @Valid CityRequestV2 cityRequest) {
        City cityActual = cityService.find(cityId);
        cityRequestDisassembler.copyToDomainObject(cityRequest, cityActual);
        return cityModelAssemblerV2.toModel(cityService.create(cityActual));
    }

    @DeleteMapping("/{cityId}")
    public void remove(@PathVariable Long cityId) {
        cityService.remove(cityId);
    }

}
