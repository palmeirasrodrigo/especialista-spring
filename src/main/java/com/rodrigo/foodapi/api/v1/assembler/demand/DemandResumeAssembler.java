package com.rodrigo.foodapi.api.v1.assembler.demand;

import com.rodrigo.foodapi.api.v1.AlgaLinks;
import com.rodrigo.foodapi.api.v1.controller.DemandController;
import com.rodrigo.foodapi.api.v1.model.response.DemandResumeResponse;
import com.rodrigo.foodapi.domain.model.Demand;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class DemandResumeAssembler extends RepresentationModelAssemblerSupport<Demand, DemandResumeResponse> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public DemandResumeAssembler() {
        super(DemandController.class, DemandResumeResponse.class);
    }

    @Override
    public DemandResumeResponse toModel(Demand demand) {
        DemandResumeResponse demandResumeResponse = createModelWithId(demand.getCode(), demand);
        modelMapper.map(demand, demandResumeResponse);

        demandResumeResponse.add(algaLinks.linkToDelivered("demands"));

        demandResumeResponse.getRestaurant().add(
                algaLinks.linkToRestaurant(demand.getRestaurant().getId()));

        demandResumeResponse.getClient().add(algaLinks.linkToUser(demand.getClient().getId()));

        return demandResumeResponse;
    }

}
