package com.rodrigo.foodapi.api.v1.model.response;

import com.rodrigo.foodapi.domain.model.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Relation(collectionRelation = "demands")
@Getter
@Setter
public class DemandResponse extends RepresentationModel<DemandResponse> {

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

    @ApiModelProperty(example = "2019-12-01T20:35:10Z")
    private OffsetDateTime confirmationDate;

    @ApiModelProperty(example = "2019-12-01T20:35:00Z")
    private OffsetDateTime cancellationDate;

    @ApiModelProperty(example = "2019-12-01T20:55:30Z")
    private OffsetDateTime deliveryDate;

    private RestaurantResumeResponse restaurant;
    private UserResponse client;
    private FormPaymentResponse formPayment;
    private AddressResponse address;
    private List<ItemDemandResponse> items;
}
