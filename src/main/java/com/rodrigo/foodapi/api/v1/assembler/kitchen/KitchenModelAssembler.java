package com.rodrigo.foodapi.api.v1.assembler.kitchen;

import com.rodrigo.foodapi.api.v1.AlgaLinks;
import com.rodrigo.foodapi.api.v1.controller.KitchenController;
import com.rodrigo.foodapi.api.v1.model.response.KitchenResponse;
import com.rodrigo.foodapi.domain.model.Kitchen;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KitchenModelAssembler extends RepresentationModelAssemblerSupport<Kitchen, KitchenResponse> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public KitchenModelAssembler() {
        super(KitchenController.class, KitchenResponse.class);
    }


    @Override
    public KitchenResponse toModel(Kitchen kitchen) {
        KitchenResponse kitchenResponse = createModelWithId(kitchen.getId(), kitchen);
        modelMapper.map(kitchen, kitchenResponse);

        kitchenResponse.add(algaLinks.linkToKitchens("kitchens"));

        return kitchenResponse;
    }

    public List<KitchenResponse> toCollectionModel(List<Kitchen> kitchens) {
        return kitchens.stream()
                .map(this::toModel)
                .toList();
    }
}
