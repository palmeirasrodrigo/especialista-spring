package com.rodrigo.foodapi.domain.event;

import com.rodrigo.foodapi.domain.model.Demand;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConfirmedOrderEvent {

    private Demand demand;
}
