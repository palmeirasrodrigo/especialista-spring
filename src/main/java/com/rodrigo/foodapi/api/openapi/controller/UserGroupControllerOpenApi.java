package com.rodrigo.foodapi.api.openapi.controller;

import com.rodrigo.foodapi.api.exceptionhandler.Problem;
import com.rodrigo.foodapi.api.model.response.GroupResponse;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Usuários")
public interface UserGroupControllerOpenApi {
    @ApiOperation("Lista os grupos associados a um usuário")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
    List<GroupResponse> list(@ApiParam(value = "ID do usuário", example = "1", required = true) Long userId);

    @ApiOperation("Desassociação de grupo com usuário")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Usuário ou grupo não encontrado",
                    response = Problem.class)
    })
    void disassociate(@ApiParam(value = "ID do usuário", example = "1", required = true) Long userId,
                      @ApiParam(value = "ID do grupo", example = "1", required = true) Long groupId);

    @ApiOperation("Associação de grupo com usuário")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Usuário ou grupo não encontrado",
                    response = Problem.class)
    })
    void associate(@ApiParam(value = "ID do usuário", example = "1", required = true) Long userId,
                   @ApiParam(value = "ID do grupo", example = "1", required = true) Long groupId);
}
