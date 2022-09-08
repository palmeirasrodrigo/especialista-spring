package com.rodrigo.foodapi.api.assembler.state;

import com.rodrigo.foodapi.api.model.response.StateResponse;
import com.rodrigo.foodapi.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StateModelAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public StateResponse toModel(State state) {
        return modelMapper.map(state, StateResponse.class);
    }

    public List<StateResponse> toCollectionModel(List<State> states) {
        return states.stream()
                .map(this::toModel)
                .toList();
    }
}
