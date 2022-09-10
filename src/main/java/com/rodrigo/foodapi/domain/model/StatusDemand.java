package com.rodrigo.foodapi.domain.model;

import java.util.Arrays;
import java.util.List;

public enum StatusDemand {
    CRIADO("Criado"),
    CONFIRMADO("confirmado", CRIADO),
    ENTREGUE("entregue", CONFIRMADO),
    CANCELADO("cancelado", CRIADO);

    private final String description;
    private List<StatusDemand> previousStatus;

    StatusDemand(String description, StatusDemand... previousStatus) {
        this.description = description;
        this.previousStatus = Arrays.asList(previousStatus);
    }

    public String getDescription() {
        return description;
    }

    public boolean cannotChangeTo(StatusDemand newStatus) {
        return !newStatus.previousStatus.contains(this);
    }

    public boolean canChangeTo(StatusDemand newStatus) {
        return !cannotChangeTo(newStatus);
    }
}
