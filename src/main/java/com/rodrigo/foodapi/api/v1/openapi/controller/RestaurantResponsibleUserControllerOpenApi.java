package com.rodrigo.foodapi.api.v1.openapi.controller;

import com.rodrigo.foodapi.api.exceptionhandler.Problem;
import com.rodrigo.foodapi.api.v1.model.response.UserResponse;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Api(tags = "Restaurantes")
public interface RestaurantResponsibleUserControllerOpenApi {
    @ApiOperation("Lista os usuários responsáveis associados a restaurante")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    CollectionModel<UserResponse> listAll(@PathVariable Long restaurantId);

    @ApiOperation("Desassociação de restaurante com usuário responsável")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante ou usuário não encontrado",
                    response = Problem.class)
    })
    ResponseEntity<Void> disassociate(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restaurantId,
                      @ApiParam(value = "ID do usuário", example = "1", required = true) Long userId);


    @ApiOperation("Associação de restaurante com usuário responsável")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante ou usuário não encontrado",
                    response = Problem.class)
    })
    ResponseEntity<Void> associate(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restaurantId,
                                   @ApiParam(value = "ID do usuário", example = "1", required = true) Long userId);
}
