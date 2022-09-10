package com.rodrigo.foodapi.api.v1.assembler.demand;

import com.rodrigo.foodapi.api.v1.AlgaLinks;
import com.rodrigo.foodapi.api.v1.controller.DemandController;
import com.rodrigo.foodapi.api.v1.model.response.DemandResponse;
import com.rodrigo.foodapi.domain.model.Demand;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class DemandAssembler extends RepresentationModelAssemblerSupport<Demand, DemandResponse> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public DemandAssembler() {
        super(DemandController.class, DemandResponse.class);
    }

    @Override
    public DemandResponse toModel(Demand demand) {
        DemandResponse demandResponse = createModelWithId(demand.getCode(), demand);
        modelMapper.map(demand, demandResponse);

        demandResponse.add(algaLinks.linkToDelivered("demands"));

        if (demand.canBeConfirm()) {
            demandResponse.add(algaLinks.linkToConfirmDemand(demand.getCode(), "confirm"));
        }

        if (demand.canBeCancel()) {
            demandResponse.add(algaLinks.linkToCancelDemand(demand.getCode(), "cancel"));
        }

        if (demand.canBeDelivered()) {
            demandResponse.add(algaLinks.linkToDeliveredDemand(demand.getCode(), "deliver"));
        }

        demandResponse.getRestaurant().add(
                algaLinks.linkToRestaurant(demand.getRestaurant().getId()));

        demandResponse.getClient().add(
                algaLinks.linkToUser(demand.getClient().getId()));

        demandResponse.getFormPayment().add(
                algaLinks.linkToFormPayment(demand.getFormPayments().getId()));

        demandResponse.getAddress().getCity().add(
                algaLinks.linkToCity(demand.getAddress().getCity().getId()));

        demandResponse.getItems().forEach(item -> {
            item.add(algaLinks.linkToProduct(
                    demandResponse.getRestaurant().getId(), item.getProductId(), "product"));
        });

        return demandResponse;
    }

}