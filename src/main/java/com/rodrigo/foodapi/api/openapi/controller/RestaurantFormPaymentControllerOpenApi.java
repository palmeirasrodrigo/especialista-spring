package com.rodrigo.foodapi.api.openapi.controller;

import com.rodrigo.foodapi.api.exceptionhandler.Problem;
import com.rodrigo.foodapi.api.model.response.FormPaymentResponse;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestaurantFormPaymentControllerOpenApi {
    @ApiOperation("Lista as formas de pagamento associadas a restaurante")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
    })
    List<FormPaymentResponse> listAll(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restaurantId);

    @ApiOperation("Desassociação de restaurante com forma de pagamento")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Desassociação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrado",
                    response = Problem.class)
    })
    void disassociate(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restaurantId,
                      @ApiParam(value = "ID da forma de pagamento", example = "1", required = true) Long formPaymentId);

    @ApiOperation("Associação de restaurante com forma de pagamento")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Associação realizada com sucesso"),
            @ApiResponse(code = 404, message = "Restaurante ou forma de pagamento não encontrado",
                    response = Problem.class)
    })
    void associate(@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restaurantId,
                   @ApiParam(value = "ID da forma de pagamento", example = "1", required = true) Long formPaymentId);
}
