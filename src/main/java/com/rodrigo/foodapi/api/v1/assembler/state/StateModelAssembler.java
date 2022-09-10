package com.rodrigo.foodapi.api.v1.assembler.state;

import com.rodrigo.foodapi.api.v1.AlgaLinks;
import com.rodrigo.foodapi.api.v1.controller.StateController;
import com.rodrigo.foodapi.api.v1.model.response.StateResponse;
import com.rodrigo.foodapi.domain.model.State;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class StateModelAssembler extends RepresentationModelAssemblerSupport<State, StateResponse> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public StateModelAssembler() {
        super(StateController.class, StateResponse.class);
    }

    @Override
    public StateResponse toModel(State state) {
        StateResponse stateResponse = createModelWithId(state.getId(), state);
        modelMapper.map(state, stateResponse);

        stateResponse.add(algaLinks.linkToStates("states"));

        return stateResponse;
    }

    @Override
    public CollectionModel<StateResponse> toCollectionModel(Iterable<? extends State> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinks.linkToStates());
    }
}
