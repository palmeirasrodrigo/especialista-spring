package com.rodrigo.foodapi.infrastructure.service.report;

import com.rodrigo.foodapi.domain.filter.DailySaleFilter;
import com.rodrigo.foodapi.domain.service.SaleQueryService;
import com.rodrigo.foodapi.domain.service.SaleReportService;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Locale;

@Service
public class PdfSaleReportService implements SaleReportService {

    @Autowired
    private SaleQueryService saleQueryService;

    @Override
    public byte[] issueSalesDaily(DailySaleFilter filter, String timeOffset) {
        try {
            var inputStream = this.getClass().getResourceAsStream("/relatorios/vendas-diarias.jasper");

            var parameters = new HashMap<String, Object>();
            parameters.put("REPORT_LOCALE", new Locale("pt", "BR"));

            var saleDaily = saleQueryService.dailySalesConsultation(filter, timeOffset);
            var dataSource = new JRBeanCollectionDataSource(saleDaily);

            var jasperPrint = JasperFillManager.fillReport(inputStream, parameters, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException e) {
            throw new ReportException("Não foi possível emitir relatório de vendas diárias", e);
        }
    }
}
