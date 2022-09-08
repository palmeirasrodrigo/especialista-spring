package com.rodrigo.foodapi.api.assembler.demand;

import com.rodrigo.foodapi.api.model.response.DemandResumeResponse;
import com.rodrigo.foodapi.domain.model.Demand;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DemandResumeAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public DemandResumeResponse toModel(Demand demand) {
        return modelMapper.map(demand, DemandResumeResponse.class);
    }

    public List<DemandResumeResponse> toCollectionModel(List<Demand> demands) {
        return demands.stream()
                .map(this::toModel)
                .toList();
    }
}
