package com.rodrigo.foodapi.api.v2.model.request;

import com.rodrigo.foodapi.api.v1.model.request.StateIdInput;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CityRequestV2 {

    @ApiModelProperty(example = "Bahia", required = true)
    @NotBlank
    private String cityName;

    @ApiModelProperty(example = "1", required = true)
    @NotNull
    private Long stateId;
}
