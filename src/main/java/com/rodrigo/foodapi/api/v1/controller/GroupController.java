package com.rodrigo.foodapi.api.v1.controller;

import com.rodrigo.foodapi.api.v1.assembler.group.GroupModelAssembler;
import com.rodrigo.foodapi.api.v1.assembler.group.GroupRequestDisassembler;
import com.rodrigo.foodapi.api.v1.model.request.GroupRequest;
import com.rodrigo.foodapi.api.v1.model.response.GroupResponse;
import com.rodrigo.foodapi.api.v1.openapi.controller.GroupControllerOpenApi;
import com.rodrigo.foodapi.domain.model.Group;
import com.rodrigo.foodapi.domain.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/v1/groups", produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupController implements GroupControllerOpenApi {

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupModelAssembler groupModelAssembler;

    @Autowired
    private GroupRequestDisassembler groupRequestDisassembler;

    @Override
    @GetMapping
    public CollectionModel<GroupResponse> listAll() {
        return groupModelAssembler.toCollectionModel(groupService.listAll());
    }

    @Override
    @GetMapping("/{groupId}")
    public GroupResponse find(@PathVariable Long groupId) {
        return groupModelAssembler.toModel(groupService.find(groupId));
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GroupResponse create(@RequestBody @Valid GroupRequest groupRequest) {
        Group group = groupRequestDisassembler.toDomainObject(groupRequest);

        return groupModelAssembler.toModel(groupService.create(group));
    }

    @Override
    @PutMapping("/{groupId}")
    public GroupResponse update(@PathVariable Long groupId, @RequestBody @Valid GroupRequest groupRequest) {
        Group actualGroup = groupService.find(groupId);
        groupRequestDisassembler.copyToDomainObject(groupRequest, actualGroup);
        return groupModelAssembler.toModel(groupService.create(actualGroup));
    }

    @Override
    @DeleteMapping("/{groupId}")
    public void remove(@PathVariable Long groupId) {
        groupService.remove(groupId);
    }
}
