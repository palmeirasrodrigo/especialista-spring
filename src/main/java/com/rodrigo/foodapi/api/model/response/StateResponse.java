package com.rodrigo.foodapi.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StateResponse {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Bahia")
    private String name;

}
