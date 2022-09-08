package com.rodrigo.foodapi.api.openapi.controller;

import com.rodrigo.foodapi.api.exceptionhandler.Problem;
import com.rodrigo.foodapi.api.model.request.FormPaymentRequest;
import com.rodrigo.foodapi.api.model.response.FormPaymentResponse;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.List;

@Api(tags = "Formas de pagamento")
public interface FormPaymentControllerOpenApi {
    @ApiOperation("Lista as formas de pagamento")
    ResponseEntity<List<FormPaymentResponse>> listAll(ServletWebRequest request);

    @ApiOperation("Busca uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da forma de pagamento inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
    })
    @GetMapping("/form-payment/{formPaymentId}")
    ResponseEntity<FormPaymentResponse> find(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long formPaymentId);

    @ApiOperation("Cadastra uma forma de pagamento")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Forma de pagamento cadastrada"),
    })
    FormPaymentResponse create(@ApiParam(name = "corpo", value = "Representação de uma nova forma de pagamento",
            required = true) FormPaymentRequest formPaymentRequest);

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Forma de pagamento atualizada"),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
    })
    FormPaymentResponse update(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long formPaymentId,
                               @ApiParam(name = "corpo", value = "Representação de uma forma de pagamento com os novos dados",
                                       required = true) FormPaymentRequest formPaymentRequest);

    @ApiOperation("Exclui uma forma de pagamento por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Forma de pagamento excluída"),
            @ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
    })
    void remove(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long formPaymentId);
}
