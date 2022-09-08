package com.rodrigo.foodapi.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class DailySale {

    private Date date;
    private Long saleAmount;
    private BigDecimal totalBilled;
}
