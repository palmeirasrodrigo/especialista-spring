package com.rodrigo.foodapi.domain.exception;

public class UserNotFoundException extends EntityNotFoundException{

    public static final String MSG_USER_NOT_FOUND = "Não existe um cadastro de usuário com código %d ";

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(Long stateId) {
        this(String.format(MSG_USER_NOT_FOUND, stateId));
    }
}
