package com.rodrigo.foodapi.api.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
public class DemandRequest {

    @Valid
    @NotNull
    private RestaurantIdInput restaurant;

    @Valid
    @NotNull
    private AddressRequest address;

    @Valid
    @NotNull
    private FormPaymentIdInput formPayments;

    @Valid
    @Size(min = 1)
    @NotNull
    private List<ItemDemandRequest> items;

}
