package com.rodrigo.foodapi.api.v1.openapi.controller;

import com.rodrigo.foodapi.api.exceptionhandler.Problem;
import com.rodrigo.foodapi.api.v1.model.request.DemandRequest;
import com.rodrigo.foodapi.api.v1.model.response.DemandResponse;
import com.rodrigo.foodapi.api.v1.model.response.DemandResumeResponse;
import com.rodrigo.foodapi.domain.filter.OrderFilter;
import io.swagger.annotations.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;

@Api(tags = "Pedidos")
public interface DemandControllerOpenApi {
    @ApiOperation("Pesquisa os pedidos")
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                    name = "campos", paramType = "query", type = "string")
    })
    PagedModel<DemandResumeResponse> search(OrderFilter filter, @PageableDefault(size = 10) Pageable pageable);


    @ApiOperation("Busca um pedido por código")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Pedido não encontrado", response = Problem.class)
    })
    @ApiImplicitParams({
            @ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por vírgula",
                    name = "campos", paramType = "query", type = "string")
    })
    DemandResponse find(@ApiParam(value = "Código de um pedido", example = "f9981ca4-5a5e-4da3-af04-933861df3e55",
            required = true) String demandCode);

    @ApiOperation("Registra um pedido")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pedido registrado"),
    })
    DemandResponse create(@ApiParam(name = "corpo", value = "Representação de um novo pedido", required = true) DemandRequest demandRequest);

}
