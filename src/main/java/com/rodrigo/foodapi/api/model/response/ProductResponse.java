package com.rodrigo.foodapi.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductResponse {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Espetinho de Cupim")
    private String name;

    @ApiModelProperty(example = "Acompanha farinha, mandioca e vinagrete")
    private String description;

    @ApiModelProperty(example = "12.50")
    private BigDecimal price;

    @ApiModelProperty(example = "true")
    private Boolean active;

}
