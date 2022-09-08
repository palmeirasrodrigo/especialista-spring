package com.rodrigo.foodapi.api.assembler.city;

import com.rodrigo.foodapi.api.model.response.CityResponse;
import com.rodrigo.foodapi.domain.model.City;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CityModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public CityResponse toModel(City city) {
        return modelMapper.map(city, CityResponse.class);
    }

    public List<CityResponse> toCollectionModel(List<City> cities) {
        return cities.stream()
                .map(this::toModel)
                .toList();
    }
}
