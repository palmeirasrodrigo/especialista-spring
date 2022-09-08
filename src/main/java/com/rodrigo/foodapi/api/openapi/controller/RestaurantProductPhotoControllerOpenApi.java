package com.rodrigo.foodapi.api.openapi.controller;

import com.rodrigo.foodapi.api.exceptionhandler.Problem;
import com.rodrigo.foodapi.api.model.request.PhotoProductRequest;
import com.rodrigo.foodapi.api.model.response.PhotoProductResponse;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import java.io.IOException;

@Api(tags = "Produtos")
public interface RestaurantProductPhotoControllerOpenApi {
    @ApiOperation("Atualiza a foto do produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Foto do produto atualizada"),
            @ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = Problem.class)
    })
    PhotoProductResponse updatePhoto(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restaurantId,
                                     @ApiParam(value = "ID do produto", example = "1", required = true) Long productId,
                                     @ApiParam(value = "Arquivo da foto do produto (máximo 500KB, apenas JPG e PNG)",
                                             required = true) PhotoProductRequest photoProductRequest) throws IOException;

    @ApiOperation(value = "Busca a foto do produto de um restaurante",
            produces = "application/json, image/jpeg, image/png")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante ou produto inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Foto de produto não encontrada", response = Problem.class)
    })
    PhotoProductResponse find(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restaurantId,
                              @ApiParam(value = "ID do produto", example = "1", required = true) Long productId);

    @ApiOperation(value = "Busca a foto do produto de um restaurante",
            produces = "application/json, image/jpeg, image/png")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID do restaurante ou produto inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Foto de produto não encontrada", response = Problem.class)
    })
    ResponseEntity<?> showPhoto(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restaurantId,
                                @ApiParam(value = "ID do produto", example = "1", required = true) Long productId,
                                String acceptHeader)
            throws HttpMediaTypeNotAcceptableException;

    @ApiOperation("Exclui a foto do produto de um restaurante")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Foto do produto excluída"),
            @ApiResponse(code = 400, message = "ID do restaurante ou produto inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Foto de produto não encontrada", response = Problem.class)
    })
    void delete(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restaurantId,
                @ApiParam(value = "ID do produto", example = "1", required = true) Long productId);

}
