package com.rodrigo.foodapi.api.v2.assembler.kitchen;

import com.rodrigo.foodapi.api.v2.model.request.KitchenRequestV2;
import com.rodrigo.foodapi.domain.model.Kitchen;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class KitchenRequestDisassemblerV2 {

    @Autowired
    private ModelMapper modelMapper;

    public Kitchen toDomainObject(KitchenRequestV2 kitchenRequest) {
        return modelMapper.map(kitchenRequest, Kitchen.class);
    }

    public void copyToDomainObject(KitchenRequestV2 kitchenRequest, Kitchen actualKitchen) {
        modelMapper.map(kitchenRequest, actualKitchen);
    }
}
