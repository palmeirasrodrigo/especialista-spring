package com.rodrigo.foodapi.api.openapi.controller;

import com.rodrigo.foodapi.api.exceptionhandler.Problem;
import com.rodrigo.foodapi.api.model.request.GroupRequest;
import com.rodrigo.foodapi.api.model.response.GroupResponse;
import io.swagger.annotations.*;

import java.util.List;

@Api(tags = "Grupos")
public interface GroupControllerOpenApi {

    @ApiOperation("Lista os grupos")
    List<GroupResponse> listAll();


    @ApiOperation("Busca um grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da grupo inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    GroupResponse find(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long groupId);


    @ApiOperation("Cadastra um grupo")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Grupo cadastrado"),
    })
    GroupResponse create(@ApiParam(name = "corpo", value = "Representação de um novo grupo", required = true) GroupRequest groupRequest);


    @ApiOperation("Atualiza um grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Grupo atualizado"),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    GroupResponse update(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long groupId,
                         @ApiParam(name = "corpo", value = "Representação de um grupo com os novos dados", required = true) GroupRequest groupRequest);


    @ApiOperation("Exclui um grupo por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Grupo excluído"),
            @ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
    })
    void remove(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long groupId);
}
