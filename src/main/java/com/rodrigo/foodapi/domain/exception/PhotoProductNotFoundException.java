package com.rodrigo.foodapi.domain.exception;

public class PhotoProductNotFoundException extends EntityNotFoundException {

    public static final String MSG_Photo_NOT_FOUND = "Não existe um cadastro de foto do produto com código %d para o restaurante de código %d";

    public PhotoProductNotFoundException(String message) {
        super(message);
    }

    public PhotoProductNotFoundException(Long productId, Long restaurantId) {
        this(String.format(MSG_Photo_NOT_FOUND, productId, restaurantId));
    }
}
