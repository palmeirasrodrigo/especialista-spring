package com.rodrigo.foodapi.api.openapi.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.rodrigo.foodapi.api.model.response.AddressResponse;
import com.rodrigo.foodapi.api.model.response.KitchenResponse;
import com.rodrigo.foodapi.api.model.view.RestaurantView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@ApiModel("RestauranteBasicoModel")
@Setter
@Getter
public class RestaurantBasicModelOpenApi {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Thai Gourmet")
    private String name;

    @ApiModelProperty(example = "12.00")
    private BigDecimal shippingFee;

    private KitchenResponse kitchen;


}
