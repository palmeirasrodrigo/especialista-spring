package com.rodrigo.foodapi.domain.exception;

public class RestaurantNotFoundException extends EntityNotFoundException{

    public static final String MSG_RESTAURANT_NOT_FOUND = "Não existe um cadastro de restaurante com código %d ";

    public RestaurantNotFoundException(String message) {
        super(message);
    }

    public RestaurantNotFoundException(Long stateId) {
        this(String.format(MSG_RESTAURANT_NOT_FOUND, stateId));
    }
}
