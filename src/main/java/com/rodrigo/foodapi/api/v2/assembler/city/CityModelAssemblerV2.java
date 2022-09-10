package com.rodrigo.foodapi.api.v2.assembler.city;

import com.rodrigo.foodapi.api.v1.AlgaLinks;
import com.rodrigo.foodapi.api.v1.controller.CityController;
import com.rodrigo.foodapi.api.v1.model.response.CityResponse;
import com.rodrigo.foodapi.api.v2.AlgaLinksV2;
import com.rodrigo.foodapi.api.v2.controller.CityControllerV2;
import com.rodrigo.foodapi.api.v2.model.response.CityResponseV2;
import com.rodrigo.foodapi.domain.model.City;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CityModelAssemblerV2 extends RepresentationModelAssemblerSupport<City, CityResponseV2> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinksV2 algaLinks;

    public CityModelAssemblerV2() {
        super(CityControllerV2.class, CityResponseV2.class);
    }

    @Override
    public CityResponseV2 toModel(City city) {
        CityResponseV2 cityResponse = createModelWithId(city.getId(), city);

        modelMapper.map(city, cityResponse);

        cityResponse.add(algaLinks.linkToCities("cities"));

        return cityResponse;
    }

    @Override
    public CollectionModel<CityResponseV2> toCollectionModel(Iterable<? extends City> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToCities());
    }
}
