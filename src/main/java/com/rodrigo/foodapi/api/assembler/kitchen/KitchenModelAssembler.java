package com.rodrigo.foodapi.api.assembler.kitchen;

import com.rodrigo.foodapi.api.model.response.KitchenResponse;
import com.rodrigo.foodapi.domain.model.Kitchen;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KitchenModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public KitchenResponse toModel(Kitchen kitchen) {
        return modelMapper.map(kitchen, KitchenResponse.class);
    }

    public List<KitchenResponse> toCollectionModel(List<Kitchen> kitchens) {
        return kitchens.stream()
                .map(this::toModel)
                .toList();
    }
}
