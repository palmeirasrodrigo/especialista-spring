package com.rodrigo.foodapi.api.v1.assembler.city;

import com.rodrigo.foodapi.api.v1.AlgaLinks;
import com.rodrigo.foodapi.api.v1.controller.CityController;
import com.rodrigo.foodapi.api.v1.model.response.CityResponse;
import com.rodrigo.foodapi.domain.model.City;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CityModelAssembler extends RepresentationModelAssemblerSupport<City, CityResponse> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public CityModelAssembler() {
        super(CityController.class, CityResponse.class);
    }

    @Override
    public CityResponse toModel(City city) {
        CityResponse cityResponse = createModelWithId(city.getId(), city);

        modelMapper.map(city, cityResponse);

        cityResponse.add(algaLinks.linkToCities("cities"));

        cityResponse.getState().add(algaLinks.linkToState(cityResponse.getState().getId()));

        return cityResponse;
    }

    @Override
    public CollectionModel<CityResponse> toCollectionModel(Iterable<? extends City> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToCities());
    }
}
