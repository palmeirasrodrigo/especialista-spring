package com.rodrigo.foodapi.api.v2.openapi;

import com.rodrigo.foodapi.api.exceptionhandler.Problem;
import com.rodrigo.foodapi.api.v2.model.request.CityRequestV2;
import com.rodrigo.foodapi.api.v2.model.response.CityResponseV2;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Api(tags = "Cidades")
public interface CityControllerV2OpenApi {
    @ApiOperation("Listar as cidades")
    public CollectionModel<CityResponseV2> listAll();

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    public CityResponseV2 find(@ApiParam(value = "ID de uma cidade", example = "1") Long cityId);

    @ApiOperation("Cadastra uma cidade")
    @ResponseStatus(HttpStatus.CREATED)
    public CityResponseV2 create(@ApiParam(name = "corpo", value = "Representação de uma nova cidade") CityRequestV2 cityRequest);

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade atualizada"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    public CityResponseV2 update(@ApiParam(value = "ID de uma cidade", example = "1") Long cityId,
                                 @ApiParam(name = "corpo", value = "Representação de uma nova cidade com os novos dados")
                                 CityRequestV2 cityRequest);

    @ApiOperation("Exclui uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cidade excluída"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)
    })
    public void remove(@ApiParam(value = "ID de uma cidade", example = "1") Long cityId);
}

