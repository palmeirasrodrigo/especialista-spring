package com.rodrigo.foodapi.domain.exception;

public class GroupNotFoundException extends EntityNotFoundException{

    public static final String MSG_GROUP_NOT_FOUND = "Não existe um cadastro de Grupo com código %d ";

    public GroupNotFoundException(String message) {
        super(message);
    }

    public GroupNotFoundException(Long stateId) {
        this(String.format(MSG_GROUP_NOT_FOUND, stateId));
    }
}
