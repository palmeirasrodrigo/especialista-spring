package com.rodrigo.foodapi.api.v2.controller;

import com.rodrigo.foodapi.api.v2.assembler.kitchen.KitchenModelAssemblerV2;
import com.rodrigo.foodapi.api.v2.assembler.kitchen.KitchenRequestDisassemblerV2;
import com.rodrigo.foodapi.api.v2.model.request.KitchenRequestV2;
import com.rodrigo.foodapi.api.v2.model.response.KitchenResponseV2;
import com.rodrigo.foodapi.api.v2.openapi.KitchenControllerV2OpenApi;
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
@RequestMapping(value = "/v2/kitchens", produces = MediaType.APPLICATION_JSON_VALUE)
public class KitchenControllerV2 implements KitchenControllerV2OpenApi {

    @Autowired
    private KitchenService kitchenService;

    @Autowired
    private KitchenModelAssemblerV2 kitchenModelAssembler;

    @Autowired
    private KitchenRequestDisassemblerV2 kitchenRequestDisassembler;

    @Autowired
    private PagedResourcesAssembler<Kitchen> pagedResourcesAssembler;

    @GetMapping
    public PagedModel<KitchenResponseV2> listAll(@PageableDefault(size = 10) Pageable pageable) {
        Page<Kitchen> kitchens = kitchenService.listAll(pageable);

        return pagedResourcesAssembler.toModel(kitchens, kitchenModelAssembler);
    }

    @GetMapping("/{kitchenId}")
    public KitchenResponseV2 find(@PathVariable Long kitchenId) {
        return kitchenModelAssembler.toModel(kitchenService.find(kitchenId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KitchenResponseV2 create(@RequestBody @Valid KitchenRequestV2 kitchenRequest) {
        Kitchen kitchen = kitchenRequestDisassembler.toDomainObject(kitchenRequest);

        return kitchenModelAssembler.toModel(kitchenService.create(kitchen));
    }

    @PutMapping("/{kitchenId}")
    public KitchenResponseV2 update(@PathVariable Long kitchenId, @RequestBody @Valid KitchenRequestV2 kitchenRequest) {
        Kitchen actualKitchen = kitchenService.find(kitchenId);
        kitchenRequestDisassembler.copyToDomainObject(kitchenRequest, actualKitchen);
        return kitchenModelAssembler.toModel(kitchenService.create(actualKitchen));
    }

    @DeleteMapping("/{kitchenId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long kitchenId) {
        kitchenService.remove(kitchenId);
    }
}
