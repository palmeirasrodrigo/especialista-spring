package com.rodrigo.foodapi.api.model.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressResponse {

    @ApiModelProperty(example = "38400-000")
    private String cep;

    @ApiModelProperty(example = "Rua Floriano Peixoto")
    private String publicPlace;

    @ApiModelProperty(example = "\"1500\"")
    private String number;

    @ApiModelProperty(example = "Apto 901")
    private String complement;

    @ApiModelProperty(example = "Centro")
    private String district;
    private CityResumeResponse city;
}
