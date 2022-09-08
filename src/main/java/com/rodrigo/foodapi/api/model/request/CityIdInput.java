package com.rodrigo.foodapi.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CityIdInput {

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    private Long id;
}
