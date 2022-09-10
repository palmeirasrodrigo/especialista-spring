package com.rodrigo.foodapi.api.v1.controller;

import com.rodrigo.foodapi.api.v1.assembler.kitchen.KitchenModelAssembler;
import com.rodrigo.foodapi.api.v1.assembler.kitchen.KitchenRequestDisassembler;
import com.rodrigo.foodapi.api.v1.model.KitchensXmlWrapper;
import com.rodrigo.foodapi.api.v1.model.request.KitchenRequest;
import com.rodrigo.foodapi.api.v1.model.response.KitchenResponse;
import com.rodrigo.foodapi.api.v1.openapi.controller.KitchenControllerOpenApi;
import com.rodrigo.foodapi.domain.model.Kitchen;
import com.rodrigo.foodapi.domain.service.KitchenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/kitchens", produces = MediaType.APPLICATION_JSON_VALUE)
public class KitchenController implements KitchenControllerOpenApi {

    @Autowired
    private KitchenService kitchenService;

    @Autowired
    private KitchenModelAssembler kitchenModelAssembler;

    @Autowired
    private KitchenRequestDisassembler kitchenRequestDisassembler;

    @Autowired
    private PagedResourcesAssembler<Kitchen> pagedResourcesAssembler;

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public PagedModel<KitchenResponse> listAll(@PageableDefault(size = 10) Pageable pageable) {
        Page<Kitchen> kitchens = kitchenService.listAll(pageable);

        return pagedResourcesAssembler.toModel(kitchens, kitchenModelAssembler);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public KitchensXmlWrapper listAllXml(Pageable pageable) {
        return new KitchensXmlWrapper(kitchenService.listAll(pageable).getContent());
    }

    @Override
    @GetMapping("/{kitchenId}")
    public KitchenResponse find(@PathVariable Long kitchenId) {
        return kitchenModelAssembler.toModel(kitchenService.find(kitchenId));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KitchenResponse create(@RequestBody @Valid KitchenRequest kitchenRequest) {
        Kitchen kitchen = kitchenRequestDisassembler.toDomainObject(kitchenRequest);

        return kitchenModelAssembler.toModel(kitchenService.create(kitchen));
    }

    @Override
    @PutMapping("/{kitchenId}")
    public KitchenResponse update(@PathVariable Long kitchenId, @RequestBody @Valid KitchenRequest kitchenRequest) {
        Kitchen actualKitchen = kitchenService.find(kitchenId);
        kitchenRequestDisassembler.copyToDomainObject(kitchenRequest, actualKitchen);
        return kitchenModelAssembler.toModel(kitchenService.create(actualKitchen));
    }

    @Override
    @DeleteMapping("/{kitchenId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long kitchenId) {
        kitchenService.remove(kitchenId);
    }
}
