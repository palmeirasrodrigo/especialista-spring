package com.rodrigo.foodapi.api.v1.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Getter
@Setter
public class RestaurantRequest {

    @ApiModelProperty(example = "Thai Gourmet", required = true)
    @NotBlank
    private String name;

    @ApiModelProperty(example = "12.00", required = true)
    @NotNull
    @PositiveOrZero
    private BigDecimal shippingFee;

    @NotNull
    @Valid
    private KitchenIdInput kitchen;

    @Valid
    @NotNull
    private AddressRequest address;

}
