package com.rodrigo.foodapi.domain.service;

import com.rodrigo.foodapi.domain.filter.DailySaleFilter;
import com.rodrigo.foodapi.domain.model.dto.DailySale;

import java.util.List;

public interface SaleQueryService {

    List<DailySale> dailySalesConsultation(DailySaleFilter filter, String timeOffset);
}
