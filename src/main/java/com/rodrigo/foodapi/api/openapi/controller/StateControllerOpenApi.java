package com.rodrigo.foodapi.api.openapi.controller;

import com.rodrigo.foodapi.api.exceptionhandler.Problem;
import com.rodrigo.foodapi.api.model.request.StateRequest;
import com.rodrigo.foodapi.api.model.response.StateResponse;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Estados")
public interface StateControllerOpenApi {
    @ApiOperation("Lista os estados")
    List<StateResponse> listAll();

    @ApiOperation("Busca um estado por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do estado inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    StateResponse find(@ApiParam(value = "ID de um estado", example = "1", required = true) Long stateId);

    @ApiOperation("Cadastra um estado")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Estado cadastrado"),
    })
    StateResponse create(@ApiParam(name = "corpo", value = "Representação de um novo estado", required = true) StateRequest stateRequest);

    @ApiOperation("Atualiza um estado por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Estado atualizado"),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    StateResponse update(@ApiParam(value = "ID de um estado", example = "1", required = true) Long stateId,
                         @ApiParam(name = "corpo", value = "Representação de um estado com os novos dados", required = true) StateRequest stateRequest);

    @ApiOperation("Exclui um estado por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Estado excluído"),
            @ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
    })
    void remove(@ApiParam(value = "ID de um estado", example = "1", required = true) Long stateId);
}
