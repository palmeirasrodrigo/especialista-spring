package com.rodrigo.foodapi.api.v1.assembler.kitchen;

import com.rodrigo.foodapi.api.v1.model.request.KitchenRequest;
import com.rodrigo.foodapi.domain.model.Kitchen;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KitchenRequestDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Kitchen toDomainObject(KitchenRequest kitchenRequest) {
        return modelMapper.map(kitchenRequest, Kitchen.class);
    }

    public void copyToDomainObject(KitchenRequest kitchenRequest, Kitchen actualKitchen) {
        modelMapper.map(kitchenRequest, actualKitchen);
    }
}
