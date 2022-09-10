package com.rodrigo.foodapi.api.v2.assembler.kitchen;

import com.rodrigo.foodapi.api.v1.controller.KitchenController;
import com.rodrigo.foodapi.api.v2.AlgaLinksV2;
import com.rodrigo.foodapi.api.v2.model.response.KitchenResponseV2;
import com.rodrigo.foodapi.domain.model.Kitchen;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KitchenModelAssemblerV2 extends RepresentationModelAssemblerSupport<Kitchen, KitchenResponseV2> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinksV2 algaLinks;

    public KitchenModelAssemblerV2() {
        super(KitchenController.class, KitchenResponseV2.class);
    }


    @Override
    public KitchenResponseV2 toModel(Kitchen kitchen) {
        KitchenResponseV2 kitchenResponse = createModelWithId(kitchen.getId(), kitchen);
        modelMapper.map(kitchen, kitchenResponse);

        kitchenResponse.add(algaLinks.linkToKitchens("kitchens"));

        return kitchenResponse;
    }

    public List<KitchenResponseV2> toCollectionModel(List<Kitchen> kitchens) {
        return kitchens.stream()
                .map(this::toModel)
                .toList();
    }
}
