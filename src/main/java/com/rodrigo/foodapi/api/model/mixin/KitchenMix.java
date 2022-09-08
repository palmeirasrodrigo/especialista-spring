package com.rodrigo.foodapi.api.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rodrigo.foodapi.domain.model.Restaurant;

import java.util.List;

public class KitchenMix {

    @JsonIgnore
    private List<Restaurant> restaurants;
}
