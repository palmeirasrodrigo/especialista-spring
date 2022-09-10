package com.rodrigo.foodapi.api.v1.assembler.demand;

import com.rodrigo.foodapi.api.v1.model.request.DemandRequest;
import com.rodrigo.foodapi.domain.model.Demand;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DemandRequestDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Demand toDomainObject(DemandRequest demandRequest) {
        return modelMapper.map(demandRequest, Demand.class);
    }

    public void copyToDomainObject(DemandRequest demandRequest, Demand demand) {
        modelMapper.map(demandRequest, demand);
    }
}
