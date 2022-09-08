package com.rodrigo.foodapi.api.openapi.controller;

import com.rodrigo.foodapi.api.exceptionhandler.Problem;
import com.rodrigo.foodapi.api.model.request.ProductRequest;
import com.rodrigo.foodapi.api.model.response.ProductResponse;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = "Produtos")
public interface RestaurantProductControllerOpenApi {
    @ApiOperation("Lista os produtos de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    List<ProductResponse> listAll(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restaurantId,
                                  @ApiParam(value = "Indica se deve ou não incluir produtos inativos no resultado da listagem",
                                          example = "false", defaultValue = "false")  boolean addInactive);

    @ApiOperation("Busca um produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante ou produto inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = Problem.class)
    })
    ProductResponse find(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restaurantId,
                         @ApiParam(value = "ID do produto", example = "1", required = true) Long productId);

    @ApiOperation("Cadastra um produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Produto cadastrado"),
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    ProductResponse add(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restaurantId,
                        @ApiParam(name = "corpo", value = "Representação de um novo produto", required = true) ProductRequest productRequest);

    @ApiOperation("Atualiza um produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Produto atualizado"),
            @ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = Problem.class)
    })
    ProductResponse update(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restaurantId,
                           @ApiParam(value = "ID do produto", example = "1", required = true) Long productId,
                           @ApiParam(name = "corpo", value = "Representação de um produto com os novos dados",
                                   required = true) ProductRequest productRequest);
}
