package com.rodrigo.foodapi.domain.service;

import com.rodrigo.foodapi.domain.filter.DailySaleFilter;

public interface SaleReportService {

    byte[] issueSalesDaily(DailySaleFilter filter, String timeOffset);
}
