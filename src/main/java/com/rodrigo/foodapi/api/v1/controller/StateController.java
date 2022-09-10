package com.rodrigo.foodapi.api.v1.controller;

import com.rodrigo.foodapi.api.v1.assembler.state.StateModelAssembler;
import com.rodrigo.foodapi.api.v1.assembler.state.StateRequestDisassembler;
import com.rodrigo.foodapi.api.v1.model.request.StateRequest;
import com.rodrigo.foodapi.api.v1.model.response.StateResponse;
import com.rodrigo.foodapi.api.v1.openapi.controller.StateControllerOpenApi;
import com.rodrigo.foodapi.domain.model.State;
import com.rodrigo.foodapi.domain.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/states", produces = MediaType.APPLICATION_JSON_VALUE)
public class StateController implements StateControllerOpenApi {

    @Autowired
    private StateService stateService;

    @Autowired
    private StateModelAssembler stateModelAssembler;

    @Autowired
    private StateRequestDisassembler stateRequestDisassembler;

    @Override
    @GetMapping
    public CollectionModel<StateResponse> listAll() {
        return stateModelAssembler.toCollectionModel(stateService.listAll());
    }

    @Override
    @GetMapping("/{stateId}")
    public StateResponse find(@PathVariable Long stateId) {
        return stateModelAssembler.toModel(stateService.find(stateId));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StateResponse create(@RequestBody @Valid StateRequest stateRequest) {
        State state = stateRequestDisassembler.toDomainObject(stateRequest);

        return stateModelAssembler.toModel(stateService.create(state));
    }

    @Override
    @PutMapping("/{stateId}")
    public StateResponse update(@PathVariable Long stateId, @RequestBody @Valid StateRequest stateRequest) {
        State actualState = stateService.find(stateId);
        stateRequestDisassembler.copyToDomainObject(stateRequest, actualState);
        return stateModelAssembler.toModel(stateService.create(actualState));
    }

    @Override
    @DeleteMapping("/state/{stateId}")
    public void remove(@PathVariable Long stateId) {
        stateService.remove(stateId);
    }
}
