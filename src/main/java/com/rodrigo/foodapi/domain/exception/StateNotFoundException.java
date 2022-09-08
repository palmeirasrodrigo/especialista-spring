package com.rodrigo.foodapi.domain.exception;

public class StateNotFoundException extends EntityNotFoundException{

    public static final String MSG_STATE_NOT_FOUND = "Não existe um cadastro de Estado com código %d ";

    public StateNotFoundException(String message) {
        super(message);
    }

    public StateNotFoundException(Long stateId) {
        this(String.format(MSG_STATE_NOT_FOUND, stateId));
    }
}
