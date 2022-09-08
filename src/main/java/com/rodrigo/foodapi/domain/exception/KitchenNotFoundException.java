package com.rodrigo.foodapi.domain.exception;

public class KitchenNotFoundException extends EntityNotFoundException{

    public static final String MSG_KITCHEN_NOT_FOUND = "Não existe um cadastro de cozinha com código %d ";

    public KitchenNotFoundException(String message) {
        super(message);
    }

    public KitchenNotFoundException(Long stateId) {
        this(String.format(MSG_KITCHEN_NOT_FOUND, stateId));
    }
}
