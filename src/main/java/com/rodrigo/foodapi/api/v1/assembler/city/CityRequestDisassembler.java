package com.rodrigo.foodapi.api.v1.assembler.city;

import com.rodrigo.foodapi.api.v1.model.request.CityRequest;
import com.rodrigo.foodapi.domain.model.City;
import com.rodrigo.foodapi.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CityRequestDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public City toDomainObject(CityRequest cityRequest) {
        return modelMapper.map(cityRequest, City.class);
    }

    public void copyToDomainObject(CityRequest cityRequest, City city) {
        city.setState(new State()); //remover o erro de mudar id
        modelMapper.map(cityRequest, city);
    }
}
