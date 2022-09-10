package com.rodrigo.foodapi.api.v1.model.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;

@ApiModel("RestauranteBasicoModel")
@Relation(collectionRelation = "restaurants")
@Setter
@Getter
public class RestaurantBasicModel extends RepresentationModel<RestaurantBasicModel> {

    @ApiModelProperty(example = "1")
    private Long id;

    @ApiModelProperty(example = "Thai Gourmet")
    private String name;

    @ApiModelProperty(example = "12.00")
    private BigDecimal shippingFee;

    private KitchenResponse kitchen;


}
