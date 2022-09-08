package com.rodrigo.foodapi.api.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class AddressRequest {

    @ApiModelProperty(example = "38400-000", required = true)
    @NotBlank
    private String cep;

    @ApiModelProperty(example = "Rua Floriano Peixoto", required = true)
    @NotBlank
    private String publicPlace;

    @ApiModelProperty(example = "\"1500\"", required = true)
    @NotBlank
    private String number;

    @ApiModelProperty(example = "Apto 901")
    @NotBlank
    private String complement;

    @ApiModelProperty(example = "Centro", required = true)
    @NotBlank
    private String district;

    @Valid
    @NotNull
    private CityIdInput city;
}
