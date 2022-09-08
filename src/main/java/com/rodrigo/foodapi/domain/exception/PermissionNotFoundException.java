package com.rodrigo.foodapi.domain.exception;

public class PermissionNotFoundException extends EntityNotFoundException{

    public static final String MSG_PERMISSION_NOT_FOUND = "Não existe um cadastro de permissão com código %d ";

    public PermissionNotFoundException(String message) {
        super(message);
    }

    public PermissionNotFoundException(Long stateId) {
        this(String.format(MSG_PERMISSION_NOT_FOUND, stateId));
    }
}
