package com.rodrigo.foodapi.api.v1.openapi.controller;

import com.rodrigo.foodapi.domain.filter.DailySaleFilter;
import com.rodrigo.foodapi.domain.model.dto.DailySale;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Api(tags = "Pedidos")
public interface StatisticsControllerOpenApi {
    @ApiOperation("Consulta estatísticas de vendas diárias")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "restaurantId", value = "ID do restaurante",
                    example = "1", dataType = "int"),
            @ApiImplicitParam(name = "createAt", value = "Data/hora inicial da criação do pedido",
                    example = "2019-12-01T00:00:00Z", dataType = "date-time"),
            @ApiImplicitParam(name = "createEnd", value = "Data/hora final da criação do pedido",
                    example = "2019-12-02T23:59:59Z", dataType = "date-time")
    })
    List<DailySale> consultDailySales(DailySaleFilter filter,
                                      @ApiParam(value = "Deslocamento de horário a ser considerado na consulta em relação ao UTC",
                                              defaultValue = "+00:00") String timeOffset);

    @GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_PDF_VALUE)
    ResponseEntity<byte[]> consultDailySalesPdf(DailySaleFilter filter, String timeOffset);
}
