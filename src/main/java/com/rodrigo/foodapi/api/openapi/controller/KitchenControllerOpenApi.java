package com.rodrigo.foodapi.api.openapi.controller;

import com.rodrigo.foodapi.api.exceptionhandler.Problem;
import com.rodrigo.foodapi.api.model.KitchensXmlWrapper;
import com.rodrigo.foodapi.api.model.request.KitchenRequest;
import com.rodrigo.foodapi.api.model.response.KitchenResponse;
import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

@Api(tags = "Cozinhas")
public interface KitchenControllerOpenApi {
    @ApiOperation("Lista as cozinhas com paginação")
    Page<KitchenResponse> listAll(@PageableDefault(size = 10) Pageable pageable);

    @ApiOperation("Lista as cozinhas com paginação em XML")
    KitchensXmlWrapper listAllXml(Pageable pageable);

    @ApiOperation("Busca uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    KitchenResponse find(@ApiParam(value = "ID de uma cozinha", example = "1", required = true) Long kitchenId);

    @ApiOperation("Cadastra uma cozinha")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cozinha cadastrada"),
    })
    KitchenResponse create(@ApiParam(name = "corpo", value = "Representação de uma nova cozinha", required = true) KitchenRequest kitchenRequest);

    @ApiOperation("Atualiza uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cozinha atualizada"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    KitchenResponse update(@ApiParam(value = "ID de uma cozinha", example = "1", required = true) Long kitchenId,
                           @ApiParam(name = "corpo", value = "Representação de uma cozinha com os novos dados",
                                   required = true) KitchenRequest kitchenRequest);

    @ApiOperation("Exclui uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cozinha excluída"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    void remove(@ApiParam(value = "ID de uma cozinha", example = "1", required = true) Long kitchenId);
}
