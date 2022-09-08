package com.rodrigo.foodapi.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class FormPaymentRequest {

    @ApiModelProperty(example = "Cartão de crédito", required = true)
    @NotBlank
    private String description;
}
