package com.rodrigo.foodapi.api.v2.openapi;

import com.rodrigo.foodapi.api.exceptionhandler.Problem;
import com.rodrigo.foodapi.api.v1.model.KitchensXmlWrapper;
import com.rodrigo.foodapi.api.v2.model.request.KitchenRequestV2;
import com.rodrigo.foodapi.api.v2.model.response.KitchenResponseV2;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;

@Api(tags = "Cozinhas")
public interface KitchenControllerV2OpenApi {
    @ApiOperation("Lista as cozinhas com paginação")
    PagedModel<KitchenResponseV2> listAll(@PageableDefault(size = 10) Pageable pageable);

    @ApiOperation("Busca uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    KitchenResponseV2 find(@ApiParam(value = "ID de uma cozinha", example = "1", required = true) Long kitchenId);

    @ApiOperation("Cadastra uma cozinha")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Cozinha cadastrada"),
    })
    KitchenResponseV2 create(@ApiParam(name = "corpo", value = "Representação de uma nova cozinha", required = true) KitchenRequestV2 kitchenRequest);

    @ApiOperation("Atualiza uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cozinha atualizada"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    KitchenResponseV2 update(@ApiParam(value = "ID de uma cozinha", example = "1", required = true) Long kitchenId,
                             @ApiParam(name = "corpo", value = "Representação de uma cozinha com os novos dados",
                                   required = true) KitchenRequestV2 kitchenRequest);

    @ApiOperation("Exclui uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cozinha excluída"),
            @ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
    })
    void remove(@ApiParam(value = "ID de uma cozinha", example = "1", required = true) Long kitchenId);
}

