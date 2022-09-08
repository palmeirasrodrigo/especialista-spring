package com.rodrigo.foodapi.api.assembler.demand;

import com.rodrigo.foodapi.api.model.response.DemandResponse;
import com.rodrigo.foodapi.domain.model.Demand;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DemandAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public DemandResponse toModel(Demand demand) {
        return modelMapper.map(demand, DemandResponse.class);
    }

    public List<DemandResponse> toCollectionModel(List<Demand> demands) {
        return demands.stream()
                .map(this::toModel)
                .toList();
    }
}
