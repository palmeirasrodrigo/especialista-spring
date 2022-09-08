package com.rodrigo.foodapi.api.openapi.controller;

import com.rodrigo.foodapi.api.exceptionhandler.Problem;
import com.rodrigo.foodapi.api.model.response.PermissionResponse;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Grupos")
public interface GroupPermissionControllerOpenApi {
    @ApiOperation("Lista as permissões associadas a um grupo")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do grupo inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    List<PermissionResponse> list(@ApiParam(value = "ID do grupo", example = "1", required = true) Long groupId);

    @ApiOperation("Desassociação de permissão com grupo")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Grupo ou permissão não encontrada",
                    response = Problem.class)
    })
    void disassociate(@ApiParam(value = "ID do grupo", example = "1", required = true) Long groupId,
                      @ApiParam(value = "ID da permissão", example = "1", required = true) @PathVariable Long permissionId);

    @ApiOperation("Associação de permissão com grupo")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Grupo ou permissão não encontrada",
                    response = Problem.class)
    })
    void associate(@ApiParam(value = "ID do grupo", example = "1", required = true) Long groupId,
                   @ApiParam(value = "ID da permissão", example = "1", required = true) @PathVariable Long permissionId);
}
