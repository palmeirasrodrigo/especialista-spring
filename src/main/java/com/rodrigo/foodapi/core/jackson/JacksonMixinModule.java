package com.rodrigo.foodapi.core.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.rodrigo.foodapi.api.model.mixin.CityMixin;
import com.rodrigo.foodapi.api.model.mixin.KitchenMix;
import com.rodrigo.foodapi.domain.model.City;
import com.rodrigo.foodapi.domain.model.Kitchen;
import com.rodrigo.foodapi.domain.model.Restaurant;
import org.springframework.stereotype.Component;

@Component
public class JacksonMixinModule extends SimpleModule {

    public JacksonMixinModule() {
        setMixInAnnotation(City.class, CityMixin.class);
        setMixInAnnotation(Kitchen.class, KitchenMix.class);
    }
}
