package com.rodrigo.foodapi.api.v1.model.response;

import com.rodrigo.foodapi.domain.model.StatusDemand;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

//@JsonFilter("demandFilter") //usado para filtra com os parâmetros que o cliente escolher
@Relation(collectionRelation = "demands")
@Getter
@Setter
public class DemandResumeResponse extends RepresentationModel<DemandResumeResponse> {

    @ApiModelProperty(example = "f9981ca4-5a5e-4da3-af04-933861df3e55")
    private String code;

    @ApiModelProperty(example = "298.90")
    private BigDecimal subtotal;

    @ApiModelProperty(example = "10.00")
    private BigDecimal shippingFee;

    @ApiModelProperty(example = "308.90")
    private BigDecimal amount;

    @ApiModelProperty(example = "CRIADO")
    private StatusDemand status;

    @ApiModelProperty(example = "2019-12-01T20:34:04Z")
    private OffsetDateTime createAt;

    private RestaurantResumeResponse restaurant;
    private UserResponse client;
}
