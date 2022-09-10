package com.rodrigo.foodapi.api.v2.assembler.city;

import com.rodrigo.foodapi.api.v1.model.request.CityRequest;
import com.rodrigo.foodapi.api.v2.model.request.CityRequestV2;
import com.rodrigo.foodapi.domain.model.City;
import com.rodrigo.foodapi.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CityRequestDisassemblerV2 {

    @Autowired
    private ModelMapper modelMapper;

    public City toDomainObject(CityRequestV2 cityRequest) {
        return modelMapper.map(cityRequest, City.class);
    }

    public void copyToDomainObject(CityRequestV2 cityRequest, City city) {
        city.setState(new State()); //remover o erro de mudar id
        modelMapper.map(cityRequest, city);
    }
}
