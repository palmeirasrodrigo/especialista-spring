package com.rodrigo.foodapi.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class StateRequest {

    @ApiModelProperty(example = "1", required = true)
    @NotBlank
    private String name;
}
