package com.rodrigo.foodapi.api.v1.openapi.controller;

import com.rodrigo.foodapi.api.v1.model.response.PermissionResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Permissões")
public interface PermissionControllerOpenApi {

    @ApiOperation("Lista as permissões")
    CollectionModel<PermissionResponse> listAll();
}
