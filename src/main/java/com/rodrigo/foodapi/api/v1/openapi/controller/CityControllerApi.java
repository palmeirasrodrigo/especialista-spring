package com.rodrigo.foodapi.api.v1.openapi.controller;

import com.rodrigo.foodapi.api.exceptionhandler.Problem;
import com.rodrigo.foodapi.api.v1.model.request.CityRequest;
import com.rodrigo.foodapi.api.v1.model.response.CityResponse;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Api(tags = "Cidades")
public interface CityControllerApi {

    @ApiOperation("Listar as cidades")
    public CollectionModel<CityResponse> listAll();

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    public CityResponse find(@ApiParam(value = "ID de uma cidade", example = "1") Long cityId);

    @ApiOperation("Cadastra uma cidade")
    @ResponseStatus(HttpStatus.CREATED)
    public CityResponse create(@ApiParam(name = "corpo", value = "Representação de uma nova cidade") CityRequest cityRequest);

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade atualizada"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    public CityResponse update(@ApiParam(value = "ID de uma cidade", example = "1") Long cityId,
                               @ApiParam(name = "corpo", value = "Representação de uma nova cidade com os novos dados")
                               CityRequest cityRequest);

    @ApiOperation("Exclui uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cidade excluída"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    public void remove(@ApiParam(value = "ID de uma cidade", example = "1") Long cityId);
}
