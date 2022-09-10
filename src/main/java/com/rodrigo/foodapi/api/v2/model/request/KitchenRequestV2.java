package com.rodrigo.foodapi.api.v2.model.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@ApiModel("KitchenInput")
@Getter
@Setter
public class KitchenRequestV2 {

    @ApiModelProperty(example = "Brasileira", required = true)
    @NotBlank
    private String name;
}
