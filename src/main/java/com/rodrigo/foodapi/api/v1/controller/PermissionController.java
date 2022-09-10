package com.rodrigo.foodapi.api.v1.controller;

import com.rodrigo.foodapi.api.v1.assembler.permission.PermissionModelAssembler;
import com.rodrigo.foodapi.api.v1.model.response.PermissionResponse;
import com.rodrigo.foodapi.api.v1.openapi.controller.PermissionControllerOpenApi;
import com.rodrigo.foodapi.domain.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissionController implements PermissionControllerOpenApi {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private PermissionModelAssembler permissionModelAssembler;

    @Override
    @GetMapping()
    public CollectionModel<PermissionResponse> listAll() {
        return permissionModelAssembler.toCollectionModel(permissionService.listAll());
    }

}