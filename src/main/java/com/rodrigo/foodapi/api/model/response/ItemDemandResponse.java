package com.rodrigo.foodapi.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemDemandResponse {

    @ApiModelProperty(example = "1")
    private Long productId;

    @ApiModelProperty(example = "Porco com molho agridoce")
    private String productName;

    @ApiModelProperty(example = "2")
    private Integer quantity;

    @ApiModelProperty(example = "78.90")
    private BigDecimal unitPrice;

    @ApiModelProperty(example = "157.80")
    private BigDecimal amount;

    @ApiModelProperty(example = "Menos picante, por favor")
    private String note;
}
