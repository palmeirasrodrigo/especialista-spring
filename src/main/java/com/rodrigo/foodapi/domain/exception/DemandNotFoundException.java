package com.rodrigo.foodapi.domain.exception;

public class DemandNotFoundException extends EntityNotFoundException {

    public static final String MSG_DEMAND_NOT_FOUND = "Não existe um cadastro de pedido com código %d ";


    public DemandNotFoundException(String demandCode) {
        super(String.format(MSG_DEMAND_NOT_FOUND, demandCode));
    }
}
