package com.rodrigo.foodapi.api.v1.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class UserWithPasswordRequest extends UserRequest{

    @ApiModelProperty(example = "123", required = true)
    @NotBlank
    private String password;
}
