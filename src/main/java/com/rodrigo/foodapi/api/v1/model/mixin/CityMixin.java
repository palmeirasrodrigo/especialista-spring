package com.rodrigo.foodapi.api.v1.model.mixin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rodrigo.foodapi.domain.model.State;

public class CityMixin {

    @JsonIgnoreProperties(value = "name", allowGetters = true)
    private State state;
}
