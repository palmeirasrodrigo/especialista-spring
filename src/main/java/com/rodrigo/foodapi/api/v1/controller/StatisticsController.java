package com.rodrigo.foodapi.api.v1.controller;

import com.rodrigo.foodapi.api.v1.AlgaLinks;
import com.rodrigo.foodapi.api.v1.openapi.controller.StatisticsControllerOpenApi;
import com.rodrigo.foodapi.domain.filter.DailySaleFilter;
import com.rodrigo.foodapi.domain.model.dto.DailySale;
import com.rodrigo.foodapi.domain.service.SaleQueryService;
import com.rodrigo.foodapi.infrastructure.service.report.PdfSaleReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/v1/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
public class StatisticsController implements StatisticsControllerOpenApi {

    @Autowired
    private SaleQueryService saleQueryService;

    @Autowired
    private PdfSaleReportService saleReportService;

    @Autowired
    private AlgaLinks algaLinks;

    @Override
    @GetMapping(path = "/daily-sales")
    public List<DailySale> consultDailySales(DailySaleFilter filter,
                                             @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        return saleQueryService.dailySalesConsultation(filter, timeOffset);
    }

    @Override
    @GetMapping(path = "/daily-sales", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> consultDailySalesPdf(DailySaleFilter filter,
                                                       @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        byte[] bytesPdf = saleReportService.issueSalesDaily(filter, timeOffset);

        var headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf"); //relatório para download

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .headers(headers)
                .body(bytesPdf);
    }
}
