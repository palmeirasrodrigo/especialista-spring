package com.rodrigo.foodapi.api.v1.openapi.controller;

import com.rodrigo.foodapi.api.exceptionhandler.Problem;
import com.rodrigo.foodapi.api.v1.model.response.FormPaymentResponse;
import io.swagger.annotations.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Restaurantes")
public interface RestaurantFormPaymentControllerOpenApi {
    @ApiOperation("Lista as formas de pagamento associadas a restaurante")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    CollectionModel<FormPaymentResponse> listAll(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restaurantId);

    @ApiOperation("Desassociação de restaurante com forma de pagamento")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrado",
                    response = Problem.class)
    })
    ResponseEntity<Void> disassociate(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restaurantId,
                                      @ApiParam(value = "ID da forma de pagamento", example = "1", required = true) Long formPaymentId);

    @ApiOperation("Associação de restaurante com forma de pagamento")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrado",
                    response = Problem.class)
    })
    ResponseEntity<Void> associate(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restaurantId,
                   @ApiParam(value = "ID da forma de pagamento", example = "1", required = true) Long formPaymentId);
}
