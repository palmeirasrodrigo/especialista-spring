package com.rodrigo.foodapi.domain.exception;

public class CityNotFoundException extends EntityNotFoundException{

    public static final String MSG_CITY_NOT_FOUND = "Não existe um cadastro de cidade com código %d ";

    public CityNotFoundException(String message) {
        super(message);
    }

    public CityNotFoundException(Long stateId) {
        this(String.format(MSG_CITY_NOT_FOUND, stateId));
    }
}
