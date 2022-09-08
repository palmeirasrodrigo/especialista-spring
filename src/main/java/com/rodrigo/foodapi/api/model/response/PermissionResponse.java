package com.rodrigo.foodapi.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermissionResponse {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "CONSULTAR_COZINHAS")
    private String name;

    @ApiModelProperty(example = "Permite consultar cozinhas")
    private String description;
}